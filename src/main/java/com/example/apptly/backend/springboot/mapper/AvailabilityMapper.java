package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.AvailabilityDto;
import com.example.apptly.backend.springboot.entity.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    @Mappings({
            @Mapping(source = "staff.id", target = "staffId"),
            @Mapping(source = "staff.email", target = "staffEmail")
    })
    AvailabilityDto toDto(Availability availability);

    @Mapping(source = "staffId", target = "staff.id")
    Availability toEntity(AvailabilityDto availabilityDto);

    List<AvailabilityDto> toDtoList(List<Availability> availabilities);
    List<Availability> toEntityList(List<AvailabilityDto> availabilityDtos);
}
