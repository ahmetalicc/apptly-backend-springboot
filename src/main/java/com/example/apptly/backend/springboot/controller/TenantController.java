package com.example.apptly.backend.springboot.controller;

import com.example.apptly.backend.springboot.common.ApiResponse;
import com.example.apptly.backend.springboot.common.ApiResponseUtil;
import com.example.apptly.backend.springboot.dto.request.TenantRequest;
import com.example.apptly.backend.springboot.dto.response.TenantResponse;
import com.example.apptly.backend.springboot.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<TenantResponse>> createTenant(@Valid @RequestBody TenantRequest tenantRequest){
        TenantResponse tenant = tenantService.createTenant(tenantRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success("Tenant has been created.", tenant, HttpStatus.CREATED));
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TenantResponse>>> getAllTenants(){
        List<TenantResponse> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(ApiResponseUtil.success("All tenants fetched", tenants));
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantResponse>> getTenantById(@PathVariable(name = "id") Long id){
        TenantResponse tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(ApiResponseUtil.success("Tenant found with id '" + id + "'", tenant));
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/byName/{name}")
    public ResponseEntity<ApiResponse<TenantResponse>> getTenantByName(@PathVariable(name = "name") String name){
        TenantResponse tenant = tenantService.getTenantByName(name);
        return ResponseEntity.ok(ApiResponseUtil.success("Tenant found with name '" + name + "'", tenant));
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantResponse>> updateTenant(@PathVariable(name = "id") Long id, @RequestBody TenantRequest tenantRequest){
        TenantResponse tenant = tenantService.updateTenant(id, tenantRequest);
        return ResponseEntity.ok(ApiResponseUtil.success("Tenant has been updated", tenant));
    }
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTenant(@PathVariable(name = "id") Long id){
        tenantService.deleteTenant(id);
        return ResponseEntity.ok(ApiResponseUtil.success("Tenant deleted with id '" + id + "'"));
    }
}
