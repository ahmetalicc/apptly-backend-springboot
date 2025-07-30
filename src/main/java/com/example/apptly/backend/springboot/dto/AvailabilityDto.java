package com.example.apptly.backend.springboot.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityDto(
        Long id,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        Long staffId,
        String staffEmail
) {
}
