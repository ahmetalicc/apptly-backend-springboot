package com.example.apptly.backend.springboot.service;

import com.example.apptly.backend.springboot.dto.UserRequest;
import com.example.apptly.backend.springboot.dto.UserResponse;
import com.example.apptly.backend.springboot.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);
    void activateUser(Long id);
    void deactivateUser(Long id);
    List<UserResponse> getAllUsersByTenant(Long tenantId);
}
