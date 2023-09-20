package com.example.holidayswap.service;

public class AccountException extends RuntimeException{
    private String errorCode;

    public AccountException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

}
