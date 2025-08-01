package com.example.apptly.backend.springboot.common;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponseUtil {

    public static <T> ApiResponse<T> success(String message, T data, HttpStatus status){
        return new ApiResponse<>(
                true,
                LocalDateTime.now(),
                status.value(),
                message,
                data
        );
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }
    public static <T> ApiResponse<T> error(String message, HttpStatus status){
        return new ApiResponse<>(
                false,
                LocalDateTime.now(),
                status.value(),
                message,
                null
        );
    }
}
