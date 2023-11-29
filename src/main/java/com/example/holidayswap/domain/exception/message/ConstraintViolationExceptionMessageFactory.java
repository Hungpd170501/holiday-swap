package com.example.holidayswap.domain.exception.message;

import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConstraintViolationExceptionMessageFactory implements ValidationErrorMessageFactory {

    @Override
    public String createValidationErrorMessage(Exception ex) {
        ConstraintViolationException violationException = (ConstraintViolationException) ex;
        return violationException.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean supports(Class<? extends Exception> exceptionType) {
        return ConstraintViolationException.class.isAssignableFrom(exceptionType);
    }
}
