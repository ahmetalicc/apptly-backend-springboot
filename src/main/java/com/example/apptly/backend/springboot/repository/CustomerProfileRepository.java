package com.example.apptly.backend.springboot.repository;

import com.example.apptly.backend.springboot.entity.CustomerProfile;
import com.example.apptly.backend.springboot.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    List<CustomerProfile> findAllByTenant(Tenant tenant);
}
