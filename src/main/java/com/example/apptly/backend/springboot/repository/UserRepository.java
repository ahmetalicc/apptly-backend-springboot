package com.example.apptly.backend.springboot.repository;

import com.example.apptly.backend.springboot.entity.Tenant;
import com.example.apptly.backend.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByTenant(Tenant tenant);
}
