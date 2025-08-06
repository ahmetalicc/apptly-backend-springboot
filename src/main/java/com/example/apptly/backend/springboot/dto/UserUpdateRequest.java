package com.example.apptly.backend.springboot.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;
    @Nullable
    private Long tenantId;
}
