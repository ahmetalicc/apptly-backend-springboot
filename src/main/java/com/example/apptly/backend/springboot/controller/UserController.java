package com.example.apptly.backend.springboot.controller;

import com.example.apptly.backend.springboot.common.ApiResponse;
import com.example.apptly.backend.springboot.common.ApiResponseUtil;
import com.example.apptly.backend.springboot.dto.UserResponse;
import com.example.apptly.backend.springboot.dto.UserUpdateRequest;
import com.example.apptly.backend.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponseUtil.success("All users fetched", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable(name = "id") Long id){
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponseUtil.success("User found with id '" + id + "'", user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        UserResponse user = userService.updateUser(id, userUpdateRequest);
        return ResponseEntity.ok(ApiResponseUtil.success("User updated successfully", user));
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<ApiResponse<String>> activateUser(@PathVariable(name = "id") Long id){
        userService.activateUser(id);
        return ResponseEntity.ok(ApiResponseUtil.success("User with id '" + id + "' activated successfully"));
    }

    @GetMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse<String>> deactivateUser(@PathVariable(name = "id") Long id){
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponseUtil.success("User with id '" + id + "' deactivated successfully"));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersByTenant(@PathVariable(name = "tenantId") Long tenantId){
        List<UserResponse> users =  userService.getAllUsersByTenant(tenantId);
        return ResponseEntity.ok(ApiResponseUtil.success("All users for tenant fetched", users));
    }
}
