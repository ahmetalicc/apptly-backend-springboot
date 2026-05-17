package com.example.apptly.backend.springboot.security;

import com.example.apptly.backend.springboot.config.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Claims claims = jwtUtil.parseClaims(token);
                if (claims != null) {
                    String email = claims.getSubject();
                    String role = claims.get("role", String.class);
                    Long tenantId = claims.get("tenantId", Long.class);
                    Long userId = claims.get("userId", Long.class);

                    if (email != null && role != null) {
                        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                        CustomUserDetails principal = new CustomUserDetails(
                                userId,
                                email,
                                null,
                                true,
                                authorities
                        );
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(auth);

                        if (tenantId != null) {
                            TenantContext.setTenantId(tenantId);
                        }
                    }
                }
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
