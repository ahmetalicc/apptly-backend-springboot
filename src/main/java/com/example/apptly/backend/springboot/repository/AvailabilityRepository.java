package com.example.apptly.backend.springboot.repository;

import com.example.apptly.backend.springboot.entity.Availability;
import com.example.apptly.backend.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByStaff(User staff);
}
