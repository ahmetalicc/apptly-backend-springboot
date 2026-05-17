# Apptly — Multi-Tenant SaaS Appointment System

Apptly, küçük hizmet işletmelerinin (kuaför, diyetisyen, masaj salonu, klinik vb.) randevu yönetimini tek bir SaaS platformu üzerinden organize etmesini sağlayan multi-tenant bir sistemdir.

## High-Level Architecture

API-first bir sistem. Backend tek bir Spring Boot servisi olarak çalışır; frontend platformları (web admin, public booking PWA, ileride native mobile) aynı API'ye bağlanır.

```
┌─────────────────────────────┐     ┌─────────────────────────────┐
│  web-admin/  (Next.js)      │     │  web-public/  (Next.js PWA) │
│  TENANT_ADMIN, STAFF         │     │  CUSTOMER (link tabanlı)    │
│  Geniş ekran dashboard      │     │  Mobile-responsive booking  │
└────────────────┬────────────┘     └────────────────┬────────────┘
                 │                                    │
                 │            REST + JWT             │
                 └─────────────────┬─────────────────┘
                                   │
                       ┌───────────▼───────────┐
                       │  backend/ (Spring 3.5) │
                       │  Multi-tenant, JWT     │
                       └───────────┬───────────┘
                                   │
                            ┌──────▼──────┐
                            │ PostgreSQL  │
                            │ (shared DB) │
                            └─────────────┘
```

### Repo Layout (Target)

Şu an repo sadece backend içeriyor. Hedef monorepo yapısı:

```
apptly/
├── backend/        # Spring Boot 3.5 (Java 17) — şu an kök seviyede, taşınacak
├── web-admin/      # Next.js App Router — TENANT_ADMIN + STAFF dashboard (PWA)
├── web-public/     # Next.js App Router — CUSTOMER booking (PWA, SEO odaklı)
├── docker-compose.yml
├── .github/workflows/
├── CLAUDE.md       # bu dosya
└── .claude/agents/ # backend / frontend / qa-test / devops alt-agent'ları
```

**Not:** Şu anki klasör adı `appointment-system-backend-spring`. İlk DevOps adımında repo monorepo'ya dönüştürülecek; backend kodu `backend/` alt klasörüne taşınacak.

## Multi-Tenancy Stratejisi

**Shared database, shared schema, tenant_id discriminator.** Her domain tablosu (`users`, `services`, `appointments`, `availabilities`, `customer_profiles`) bir `tenant_id` FK taşır. `Role` ve `Tenant` tabloları global'dir.

### Tenant Bağlamı (KRİTİK GÜVENLİK KURALI)

Her authenticated request için `TenantContext` (ThreadLocal) doldurulur. Tüm tenant-scoped sorgular bu bağlamı zorunlu olarak kullanır.

```java
// JwtAuthFilter doldurur, RequestInterceptor temizler
TenantContext.set(jwtClaims.get("tenantId"));

// Repository veya Hibernate Filter seviyesinde uygulanır
@Query("SELECT a FROM Appointment a WHERE a.tenant.id = :tenantId AND ...")
```

**Asla** bir endpoint path'inde `tenantId` parametresi alıp ona göre query yapma — JWT'deki tenant ile path'teki tenant farklıysa request reddedilir. SUPER_ADMIN bunun istisnasıdır (tüm tenant'lara erişebilir).

### Roller

| Rol | Erişim | Arayüz |
|---|---|---|
| `ROLE_SUPER_ADMIN` | Tüm tenant'lar, tenant CRUD, sistem yönetimi | web-admin (özel alan) |
| `ROLE_TENANT_ADMIN` | Kendi tenant'ının tüm verisi, staff/service/availability yönetimi | web-admin |
| `ROLE_TENANT_STAFF` | Kendi randevuları + kendi availability'si | web-admin (responsive/PWA) |
| `ROLE_CUSTOMER` | Kendi randevuları, public booking | web-public |

## Backend Stack

- **Java 17 + Spring Boot 3.5.4** — `spring-boot-starter-{web, data-jpa, security, validation}`
- **PostgreSQL** — `application.yaml` üzerinden `.env` ile config
- **JWT** — `io.jsonwebtoken:jjwt 0.11.5`, HS256, claims: `sub=email, role, tenantId`
- **Lombok + MapStruct 1.5.5** — annotation processor sıralaması Lombok → MapStruct
- **springdoc-openapi 2.8.9** — `/swagger-ui.html`
- **Flyway** — eklenecek (`ddl-auto: update` production-safe değil)

### Backend Package Layout

```
com.example.apptly.backend.springboot
├── common/        # ApiResponse, ApiResponseUtil
├── config/        # SecurityConfig, DataInitializer, CustomUserDetails, TenantContext (eklenecek)
├── controller/
├── dto/
│   ├── request/   # *Request — incoming payload
│   └── response/  # *Response — outgoing payload
├── entity/
├── enums/
├── exception/     # *Exception + GlobalExceptionHandler
├── mapper/        # MapStruct
├── repository/
├── security/      # JwtUtil, JwtAuthFilter, TenantInterceptor (eklenecek)
└── service/
    └── serviceImpl/
```

### Backend Convention'ları

- **DTO ayrımı**: request payload'ları `dto/request/*Request.java`, response'lar `dto/response/*Response.java`. Eski flat `*Dto` sınıfları (`AppointmentDto`, `AvailabilityDto`, `ServiceDto`) `request/response` ayrımına refactor edilecek.
- **Validation**: `@Valid` controller seviyesinde, `@NotBlank/@NotNull/@Email` request DTO'larında. Business validation service'te.
- **Exception handling**: Tüm hatalar `GlobalExceptionHandler` üzerinden `ApiResponse` ile döner. Yeni exception sınıfı eklerken handler'a da satır eklenir.
- **API contract**: Tüm response'lar `ApiResponse<T>` (success, status, message, timestamp, data) sarmalında döner.
- **MapStruct**: Manuel mapping yazma — `*Mapper` interface'i içine method ekle.
- **Repository**: Spring Data method naming + gerektiğinde `@Query`. Native query'den kaçın.
- **Service interface ayrımı**: `service/XxxService.java` interface, `service/serviceImpl/XxxServiceImpl.java` implementation. Yeni servis bu pattern'i izler.

### Bilinen Backend Açıkları (henüz çözülmedi)

Frontend'e geçmeden önce bunların **tamamı kapatılır**. Backend agent'ı sırayla işler:

1. **🔴 Tenant izolasyonu yok** — `TenantContext` + `TenantInterceptor` kurulacak; tüm tenant-scoped repository'ler bağlamı zorunlu hale getirecek.
2. **🔴 Appointment çakışma kontrolü yok** — `Appointment.endTime` (veya `Service.durationMinutes`) eklenecek; aynı staff için zaman çakışması service katmanında reddedilecek.
3. **🟡 `Service` entity ismi** Spring'in `@Service` ile karışıyor → `ServiceOffering` veya `SalonService` olarak rename.
4. **🟡 Flyway** entegrasyonu, `ddl-auto: validate`'e geçiş.
5. **🟡 `JwtAuthFilter` her request'te DB'ye gidiyor** — JWT claim'lerinden direkt auth nesnesi kurulacak.
6. **🟡 Eksik controller'lar**: `AppointmentController`, `AvailabilityController`, `ServiceController` (rename sonrası). Public booking için ayrı `/api/public/**` namespace.
7. **🟡 Customer self-registration flow** + tenant onboarding flow.
8. **🟡 `UserServiceImpl`**: `roleRepository` field'ı constructor'dan sonra tanımlanmış, yukarı taşınacak.
9. **🟡 `CustomerProfile.phone` tenant bazlı unique constraint** yok.
10. **🟡 `JwtUtil.validateToken` token'ı iki kere parse ediyor** — tek geçişte yapacak.

## Frontend Stack (Henüz Başlamadı)

- **Next.js 14+ (App Router) + TypeScript + Tailwind CSS**
- **State**: Server Components default, client state için Zustand veya TanStack Query
- **Form**: `react-hook-form` + `zod` validation
- **API client**: `fetch` wrapper + JWT interceptor; tip güvenliği için **OpenAPI'dan TS tip üretimi** (`openapi-typescript`)
- **PWA**: `next-pwa` veya manuel manifest + service worker
- **i18n**: TR/EN, `next-intl`

### Frontend Hedef Yapı

```
web-admin/ (TENANT_ADMIN + STAFF)
└── src/app/
    ├── (auth)/login/
    ├── dashboard/
    ├── appointments/  # haftalık/aylık grid
    ├── staff/
    ├── services/
    └── settings/

web-public/ (CUSTOMER booking)
└── src/app/
    ├── [tenantSlug]/        # kuafor-mehmet.apptly.com
    │   ├── page.tsx         # işletme landing
    │   ├── book/
    │   └── my-appointments/
    └── (auth)/
```

## Komutlar

### Backend

```powershell
# Çalıştır
.\mvnw spring-boot:run

# Build
.\mvnw clean package -DskipTests

# Test
.\mvnw test

# Swagger
# http://localhost:8080/swagger-ui.html
```

### Frontend (kurulduğunda)

```powershell
cd web-admin
npm install
npm run dev          # localhost:3000
npm run build
npm run typecheck
npm run lint
```

### Docker (kurulduğunda)

```powershell
docker compose up -d            # postgres + backend
docker compose logs -f backend
```

## Geliştirme Akışı (Solo Dev, 1-2 saat/gün)

1. **Sprint** kafası — 1 hafta = 1 küçük feature (örn. "Appointment CRUD" veya "Availability haftalık grid")
2. Her oturum başında `git pull`, `mvnw test`, `npm run typecheck`
3. Feature branch: `feat/<area>-<kısa-isim>`, commit konvansiyonu `feat: ...` / `fix: ...` / `chore: ...` (mevcut history'ye uygun)
4. PR yok (solo) ama her tamamlanan feature'da kendine 5dk code review
5. Her oturum sonunda: testler yeşil, build yeşil, dev server çalışıyor → commit + push

## Karşılaşılan Riskler ve Kararlar

- **Native mobile yok (MVP)**: Müşteri Instagram link'ten geleceği için PWA seçildi. Native app, ürün oturduktan sonra `mobile/` klasörü olarak eklenir (Flutter veya React Native). Backend API'sı **mobile-ready** kalır.
- **Tenant onboarding flow**: SUPER_ADMIN manuel ekler (MVP). İleride self-service signup + ödeme entegrasyonu (Iyzico/Stripe).
- **Notification**: E-posta önce (SMTP), SMS ve push ileride.

## Agent'lar

`.claude/agents/` altında 4 alt-agent tanımlı:

- **backend-engineer** — Spring/JPA/Security tarafı; multi-tenant izolasyon uzmanı
- **frontend-engineer** — Next.js App Router, Tailwind, accessibility, API entegrasyonu
- **qa-test** — JUnit + Testcontainers (backend), Playwright (E2E), regression guard
- **devops** — Docker, docker-compose, GitHub Actions, deploy, .env, monitoring

Bu agent'lara delegasyon, ilgili alanda **derin iş** veya **bağımsız paralel iş** için tercih edilir. Hızlı 2-3 satırlık değişiklik için kök Claude yeterli.
