package com.example.holidayswap.domain.exception;

public class DistributionLockException extends RuntimeException {

    public DistributionLockException(String message) {
        super(message);
    }
}