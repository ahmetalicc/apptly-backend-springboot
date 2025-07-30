package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.ServiceDto;
import com.example.apptly.backend.springboot.entity.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "tenant.name", target = "tenantName")
    ServiceDto toDto(Service service);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Service toEntity(ServiceDto serviceDto);

    List<ServiceDto> toDtoList(List<Service> services);
    List<Service> toServiceList(List<ServiceDto> serviceDtos);

}
