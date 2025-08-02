package com.example.apptly.backend.springboot.common;

import org.springframework.http.HttpStatus;

public class ApiResponseUtil {

    public static <T> ApiResponse<T> success(String message, T data, HttpStatus status){
        return new ApiResponse<>(
                true,
                status.value(),
                message,
                data
        );
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, HttpStatus.OK);
    }

    public static<T> ApiResponse<T> success(String message){
        return success(message, null, HttpStatus.OK);
    }
    public static <T> ApiResponse<T> error(String message, HttpStatus status){
        return new ApiResponse<>(
                false,
                status.value(),
                message,
                null
        );
    }
}
