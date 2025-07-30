package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.RoleDto;
import com.example.apptly.backend.springboot.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);

    List<RoleDto> toDtoList(List<Role> roles);
    List<Role> toEntityList(List<RoleDto> roleDtos);
}
