package com.example.apptly.backend.springboot.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiResponse <T>{
    private boolean success;
    private LocalDateTime timeStamp = LocalDateTime.now();
    private int status;
    private String message;
    private T data;

    public ApiResponse(boolean success, int status, String message, T data){
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
