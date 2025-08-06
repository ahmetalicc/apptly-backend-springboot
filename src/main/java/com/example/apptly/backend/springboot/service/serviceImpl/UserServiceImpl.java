package com.example.apptly.backend.springboot.service.serviceImpl;

import com.example.apptly.backend.springboot.dto.UserRequest;
import com.example.apptly.backend.springboot.dto.UserResponse;
import com.example.apptly.backend.springboot.dto.UserUpdateRequest;
import com.example.apptly.backend.springboot.entity.Role;
import com.example.apptly.backend.springboot.entity.Tenant;
import com.example.apptly.backend.springboot.entity.User;
import com.example.apptly.backend.springboot.exception.BadRequestException;
import com.example.apptly.backend.springboot.exception.NullHandleException;
import com.example.apptly.backend.springboot.exception.ResourceNotFoundException;
import com.example.apptly.backend.springboot.mapper.UserMapper;
import com.example.apptly.backend.springboot.repository.RoleRepository;
import com.example.apptly.backend.springboot.repository.TenantRepository;
import com.example.apptly.backend.springboot.repository.UserRepository;
import com.example.apptly.backend.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new BadRequestException("Email already in use");
        }

        Tenant tenant = null;
        if(userRequest.getTenantId() != null){
             tenant = tenantRepository.findById(userRequest.getTenantId()).orElseThrow(
                    () ->  new ResourceNotFoundException("Tenant not found with id '" + userRequest.getTenantId() + "'"));
        }
        if(userRequest.getRoleId() == null){
            throw new NullHandleException("Role id can not be null");
        }
        Role role = roleRepository.findById(userRequest.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role not found with id '" + userRequest.getRoleId() + "'"));

        User user = userMapper.toEntity(userRequest, tenant, role);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    private final RoleRepository roleRepository;
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException("Users not found");
        }
        return userMapper.toResponseList(users);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id '" + id + "'"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id '" + id + "'"));

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());

        if(!user.getEmail().equals(userUpdateRequest.getEmail())){
            if(userRepository.existsByEmail(userUpdateRequest.getEmail())){
                throw new BadRequestException("Email already exists!");
            }
            user.setEmail(userUpdateRequest.getEmail());
        }

        if(userUpdateRequest.getTenantId() != null){
            Long tenantId = userUpdateRequest.getTenantId();
            Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(
                    ()-> new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'"));
            user.setTenant(tenant);
        }

        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public void activateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id '" + id + "'"));
        if(user.isActive()){
            throw new BadRequestException("User already active");
        }
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id '" + id + "'"));
        if(!user.isActive()){
            throw new BadRequestException("User already inactive");
        }
        user.setActive(false);
        userRepository.save(user);
    }
    @Override
    public List<UserResponse> getAllUsersByTenant(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(
                () -> new ResourceNotFoundException("Tenant not found with id '" + tenantId + "'"));
        List<User> users = userRepository.findAllByTenant(tenant);
        if(users.isEmpty()){
            throw new ResourceNotFoundException("Users not found for this tenant");
        }
        return userMapper.toResponseList(users);
    }
}
