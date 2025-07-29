package com.example.apptly.backend.springboot.repository;

import com.example.apptly.backend.springboot.entity.Service;
import com.example.apptly.backend.springboot.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByTenant(Tenant tenant);
}
