package com.example.apptly.backend.springboot.dto;

import com.example.apptly.backend.springboot.entity.CustomerProfile;
import com.example.apptly.backend.springboot.entity.Service;
import com.example.apptly.backend.springboot.entity.Tenant;
import com.example.apptly.backend.springboot.entity.User;
import com.example.apptly.backend.springboot.enums.AppointmentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {

    private Long id;
    private LocalDateTime appointmentTime;
    private AppointmentStatus appointmentStatus;

    private Long serviceId;
    private String serviceName;

    private Long staffId;
    private String staffEmail;

    private Long tenantId;
    private String tenantName;

    private Long customerProfileId;
    private String customerFullname;
}
