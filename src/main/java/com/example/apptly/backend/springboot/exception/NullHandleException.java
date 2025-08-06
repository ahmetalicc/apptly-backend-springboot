package com.example.apptly.backend.springboot.exception;

public class NullHandleException extends NullPointerException{
    public NullHandleException(String message){
        super(message);
    }
}
