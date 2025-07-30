package com.example.apptly.backend.springboot.mapper;

import com.example.apptly.backend.springboot.dto.UserDto;
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
    @Mapping(source = "customerProfile.id", target = "customerProfileId")
    @Mapping(source = "customerProfile.fullname", target = "customerProfileFullname")
    UserDto toDto(User user);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "customerProfile", ignore = true)
    @Mapping(target = "staffAppointments", ignore = true)
    @Mapping(target = "availabilities", ignore = true)
    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> users);
    List<User> toEntityList(List<UserDto> userDtos);
}
