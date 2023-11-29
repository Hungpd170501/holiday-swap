package com.example.holidayswap.domain.exception.message;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MethodArgumentNotValidExceptionMessageFactory implements ValidationErrorMessageFactory {

    @Override
    public String createValidationErrorMessage(Exception ex) {
        MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) ex;
        List<FieldError> fieldErrors = notValidException.getBindingResult().getFieldErrors();
        return fieldErrors.stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
    }
    @Override
    public boolean supports(Class<? extends Exception> exceptionType) {
        return MethodArgumentNotValidException.class.isAssignableFrom(exceptionType);
    }
}