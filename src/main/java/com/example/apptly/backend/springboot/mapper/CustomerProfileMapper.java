package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.CustomerProfileDto;
import com.example.apptly.backend.springboot.entity.CustomerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerProfileMapper {
    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "tenant.name", target = "tenantName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    CustomerProfileDto toDto(CustomerProfile customerProfile);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "user", ignore = true)
    CustomerProfile toEntity(CustomerProfileDto customerProfileDto);

    List<CustomerProfileDto> toDtoList(List<CustomerProfile> customerProfiles);
    List<CustomerProfile> toEntityList(List<CustomerProfileDto> customerProfileDtos);
}
