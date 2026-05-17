package com.example.apptly.backend.springboot.security;

import com.example.apptly.backend.springboot.exception.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TenantSecurity {

    private static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    public boolean isSuperAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return false;
        }
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (ROLE_SUPER_ADMIN.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public Long currentTenantId() {
        return TenantContext.getTenantId();
    }

    public void requireAccessToTenant(Long tenantId) {
        if (isSuperAdmin()) {
            return;
        }
        Long current = TenantContext.getTenantId();
        if (current == null || !current.equals(tenantId)) {
            throw new ForbiddenException("Access denied for tenant '" + tenantId + "'");
        }
    }
}
