package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.TenantDto;
import com.example.apptly.backend.springboot.entity.Tenant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantDto toDto(Tenant tenant);
    Tenant toEntity(TenantDto tenantDto);

    List<TenantDto> toDtoList(List<Tenant> tenants);
    List<Tenant> toEntityList(List<TenantDto> tenantDtos);
}
