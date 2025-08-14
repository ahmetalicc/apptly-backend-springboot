package com.example.apptly.backend.springboot.service;

import com.example.apptly.backend.springboot.dto.TenantRequest;
import com.example.apptly.backend.springboot.dto.TenantResponse;

import java.util.List;

public interface TenantService {
    TenantResponse createTenant(TenantRequest tenantRequest);
    List<TenantResponse> getAllTenants();
    TenantResponse getTenantById(Long id);
    TenantResponse getTenantByName(String name);
    TenantResponse updateTenant(Long id, TenantRequest tenantRequest);
    void deleteTenant(Long id);
}
