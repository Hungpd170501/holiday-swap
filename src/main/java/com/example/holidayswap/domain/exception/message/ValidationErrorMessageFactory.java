package com.example.holidayswap.domain.exception.message;

public interface ValidationErrorMessageFactory {
    String createValidationErrorMessage(Exception ex);
    boolean supports(Class<? extends Exception> exceptionType);
}
