package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.UserRequest;
import com.example.apptly.backend.springboot.dto.UserResponse;
import com.example.apptly.backend.springboot.entity.Role;
import com.example.apptly.backend.springboot.entity.Tenant;
import com.example.apptly.backend.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "tenant.id", target = "tenantId")
    @Mapping(source = "tenant.name", target = "tenantName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
    default User toEntity(UserRequest userRequest, Tenant tenant, Role role){
        return User.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .isActive(userRequest.getIsActive() != null ? userRequest.getIsActive() : false)
                .tenant(tenant)
                .role(role)
                .build();
    }
}
