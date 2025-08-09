package com.example.apptly.backend.springboot.controller;

import com.example.apptly.backend.springboot.common.ApiResponse;
import com.example.apptly.backend.springboot.common.ApiResponseUtil;
import com.example.apptly.backend.springboot.dto.AuthRequest;
import com.example.apptly.backend.springboot.dto.AuthResponse;
import com.example.apptly.backend.springboot.dto.UserRequest;
import com.example.apptly.backend.springboot.dto.UserResponse;
import com.example.apptly.backend.springboot.service.AuthService;
import com.example.apptly.backend.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.ok(ApiResponseUtil.success("Login success", authResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody UserRequest userRequest){
        UserResponse user = userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseUtil.success("User registered successfully", user, HttpStatus.CREATED));
    }
}
