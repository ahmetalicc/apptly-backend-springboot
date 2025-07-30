package com.example.apptly.backend.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerProfileDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String notes;

    private Long tenantId;
    private String tenantName;

    private Long userId;
    private String userEmail;
}
