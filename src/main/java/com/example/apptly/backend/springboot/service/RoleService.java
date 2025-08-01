package com.example.apptly.backend.springboot.service;

import com.example.apptly.backend.springboot.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> gelAll();
    RoleDto getRoleByName(String name);
}
