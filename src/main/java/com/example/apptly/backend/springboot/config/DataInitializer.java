package com.example.apptly.backend.springboot.config;

import com.example.apptly.backend.springboot.entity.Role;
import com.example.apptly.backend.springboot.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        List<String> rolesToCreate = List.of(
                "ROLE_SUPER_ADMIN",
                "ROLE_TENANT_ADMIN",
                "ROLE_TENANT_STAFF",
                "ROLE_CUSTOMER"
        );
        for(String role : rolesToCreate){
            roleRepository.findByName(role).orElseGet(
                    ()-> {
                        Role newRole = new Role();
                        newRole.setName(role);
                        return roleRepository.save(newRole);
                    });
        }
        System.out.println("Roles are saved successfully.");
    }
}
