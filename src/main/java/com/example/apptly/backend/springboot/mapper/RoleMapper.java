package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.RoleRequest;
import com.example.apptly.backend.springboot.dto.RoleResponse;
import com.example.apptly.backend.springboot.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(Role role);
    Role toEntity(RoleRequest roleRequest);

    List<RoleResponse> toResponseList(List<Role> roles);
    List<Role> toEntityList(List<RoleRequest> roleRequests);
}
