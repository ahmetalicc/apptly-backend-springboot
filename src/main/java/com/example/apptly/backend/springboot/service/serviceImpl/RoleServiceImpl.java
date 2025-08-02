package com.example.apptly.backend.springboot.service.serviceImpl;

import com.example.apptly.backend.springboot.dto.RoleRequest;
import com.example.apptly.backend.springboot.dto.RoleResponse;
import com.example.apptly.backend.springboot.entity.Role;
import com.example.apptly.backend.springboot.exception.BadRequestException;
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
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        if(roleRepository.existsByName(roleRequest.getName())){
            throw new BadRequestException("Role already exists");
        }
        Role role = roleMapper.toEntity(roleRequest);
        roleRepository.save(role);
        return roleMapper.toResponse(role);
    }
    @Override
    public List<RoleResponse> gelAll() {
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()){
            throw new ResourceNotFoundException("Roles not found");
        }
        return roleMapper.toResponseList(roles);
    }

    @Override
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Role with name '"+ name +"' not found "));
        return roleMapper.toResponse(role);
    }

    @Override
    public void deleteRole(Long id) {
        if(!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id '" + id + "'");
        }
        roleRepository.deleteById(id);
    }
}
