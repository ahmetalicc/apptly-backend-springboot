package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.TenantRequest;
import com.example.apptly.backend.springboot.dto.TenantResponse;
import com.example.apptly.backend.springboot.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantResponse toResponse(Tenant tenant);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "customers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Tenant toEntity(TenantRequest tenantRequest);
    List<TenantResponse> toResponseList(List<Tenant> tenants);
}
