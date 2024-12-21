package com.example.hci.exception;

public class HCIException extends RuntimeException {
    public HCIException(String message) {
        super(message);
    }
    public String getMessage(){
        return super.getMessage();
    }
}
