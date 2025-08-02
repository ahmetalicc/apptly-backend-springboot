package com.example.apptly.backend.springboot.service;

import com.example.apptly.backend.springboot.dto.RoleRequest;
import com.example.apptly.backend.springboot.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> gelAll();
    RoleResponse getRoleByName(String name);
    RoleResponse createRole(RoleRequest roleRequest);
    void deleteRole(Long id);
}
