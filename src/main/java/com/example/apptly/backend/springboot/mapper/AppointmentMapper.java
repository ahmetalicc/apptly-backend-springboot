package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.AppointmentDto;
import com.example.apptly.backend.springboot.entity.Appointment;
import org.aspectj.asm.IModelFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "service.name", target = "serviceName")
    @Mapping(source = "staff.id", target = "staffId")
    @Mapping(source = "staff.email", target = "staffEmail")
    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "tenant.name", target = "tenantName")
    @Mapping(source = "customerProfile.id", target = "customerProfileId")
    @Mapping(source = "customerProfile.fullname", target = "customerProfileFullname")
    AppointmentDto toDto(Appointment appointment);

    @Mapping(target = "service", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "customerProfile", ignore = true)
    Appointment toEntity(AppointmentDto appointmentDto);

    List<AppointmentDto> toDtoList(List<Appointment> appointments);
    List<Appointment> toEntityList(List<AppointmentDto> appointmentDtos);
}
