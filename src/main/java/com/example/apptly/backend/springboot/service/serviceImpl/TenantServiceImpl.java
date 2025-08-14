package com.example.apptly.backend.springboot.service.serviceImpl;

import com.example.apptly.backend.springboot.dto.TenantRequest;
import com.example.apptly.backend.springboot.dto.TenantResponse;
import com.example.apptly.backend.springboot.entity.Tenant;
import com.example.apptly.backend.springboot.exception.BadRequestException;
import com.example.apptly.backend.springboot.exception.ResourceNotFoundException;
import com.example.apptly.backend.springboot.mapper.TenantMapper;
import com.example.apptly.backend.springboot.repository.TenantRepository;
import com.example.apptly.backend.springboot.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    @Override
    public TenantResponse createTenant(TenantRequest tenantRequest) {
        String name = tenantRequest.getName();
        tenantRepository.findByName(name).ifPresent(t -> {
            throw new BadRequestException("Tenant already exists with name '" + name + "'");
        });
        Tenant tenant = tenantMapper.toEntity(tenantRequest);
        tenantRepository.save(tenant);
        return tenantMapper.toResponse(tenant);
    }
    @Override
    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        if (tenants.isEmpty()) {
            throw new ResourceNotFoundException("Tenants not found");
        }
        return tenantMapper.toResponseList(tenants);
    }
    @Override
    public TenantResponse getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tenant not found with id: '" + id + "'"));
        return tenantMapper.toResponse(tenant);
    }

    @Override
    public TenantResponse getTenantByName(String name) {
        Tenant tenant = tenantRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Tenant not found with name: '" + name + "'"));
        return tenantMapper.toResponse(tenant);
    }
    @Override
    public TenantResponse updateTenant(Long id, TenantRequest tenantRequest) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tenant not found with id: '" + id + "'"));

        String newName = tenantRequest.getName();
        if(newName != null && !newName.trim().isEmpty() && !newName.equalsIgnoreCase(tenant.getName())){
            tenantRepository.findByName(newName).ifPresent(existing -> {
                if(!existing.getId().equals(id)){
                    throw new BadRequestException("Another tenant already exist with name '" + newName + "'");
                }
            });
            tenant.setName(newName);
        }
        tenantRepository.save(tenant);
        return tenantMapper.toResponse(tenant);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tenant not found with id: '" + id + "'"));
        tenantRepository.delete(tenant);
    }
}
