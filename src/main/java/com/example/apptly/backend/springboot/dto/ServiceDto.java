package com.example.apptly.backend.springboot.dto;

public record ServiceDto(
        Long id,
        String name,
        String description,
        double price,
        Long tenantId,
        String tenantName
) {
}
