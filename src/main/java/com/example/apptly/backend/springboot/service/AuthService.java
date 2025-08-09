package com.example.apptly.backend.springboot.service;

import com.example.apptly.backend.springboot.dto.AuthRequest;
import com.example.apptly.backend.springboot.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
