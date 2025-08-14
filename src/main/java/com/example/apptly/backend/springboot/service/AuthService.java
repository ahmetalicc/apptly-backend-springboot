package com.example.apptly.backend.springboot.service;

import com.example.apptly.backend.springboot.dto.request.AuthRequest;
import com.example.apptly.backend.springboot.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
