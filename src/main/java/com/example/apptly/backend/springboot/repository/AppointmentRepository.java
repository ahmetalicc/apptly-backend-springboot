package com.example.apptly.backend.springboot.repository;

import com.example.apptly.backend.springboot.entity.Appointment;
import com.example.apptly.backend.springboot.entity.CustomerProfile;
import com.example.apptly.backend.springboot.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByTenant(Tenant tenant);
    List<Appointment> findAllByCustomer(CustomerProfile customerProfile);
}
