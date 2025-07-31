package com.example.apptly.backend.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{
    private boolean success;
    private LocalDateTime timeStamp = LocalDateTime.now();
    private int status;
    private String message;
    private T data;

}
