package com.example.apptly.backend.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String email;
    private boolean isActive;
    private LocalDateTime createdAt;

    private Long tenantId;
    private String tenantName;

    private Long roleId;
    private String roleName;

    private Long customerProfileId;
    private String customerProfileFullname;
}
