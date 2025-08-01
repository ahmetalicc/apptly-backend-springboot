package com.example.apptly.backend.springboot.service.serviceImpl;

import com.example.apptly.backend.springboot.dto.RoleDto;
import com.example.apptly.backend.springboot.entity.Role;
import com.example.apptly.backend.springboot.exception.ResourceNotFoundException;
import com.example.apptly.backend.springboot.mapper.RoleMapper;
import com.example.apptly.backend.springboot.repository.RoleRepository;
import com.example.apptly.backend.springboot.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    @Override
    public List<RoleDto> gelAll() {
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()){
            throw new ResourceNotFoundException("Roles not found");
        }
        return roleMapper.toDtoList(roles);
    }

    @Override
    public RoleDto getRoleByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Role with name '"+ name +"' not found "));
        return roleMapper.toDto(role);
    }
}
