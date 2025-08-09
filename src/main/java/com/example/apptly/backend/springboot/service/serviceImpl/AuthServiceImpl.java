package com.example.apptly.backend.springboot.service.serviceImpl;

import com.example.apptly.backend.springboot.dto.AuthRequest;
import com.example.apptly.backend.springboot.dto.AuthResponse;
import com.example.apptly.backend.springboot.dto.UserResponse;
import com.example.apptly.backend.springboot.entity.User;
import com.example.apptly.backend.springboot.exception.BadRequestException;
import com.example.apptly.backend.springboot.exception.ResourceNotFoundException;
import com.example.apptly.backend.springboot.mapper.UserMapper;
import com.example.apptly.backend.springboot.repository.UserRepository;
import com.example.apptly.backend.springboot.security.JwtUtil;
import com.example.apptly.backend.springboot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Override
    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email '" + authRequest.getEmail() + "'"));
        if(!user.isActive() || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new BadRequestException("Invalid credentials or inactive user!");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getName(), user.getTenant() != null ? user.getTenant().getId() : null);
        UserResponse userResponse = userMapper.toResponse(user);
        return new AuthResponse(token, userResponse);
    }
}
