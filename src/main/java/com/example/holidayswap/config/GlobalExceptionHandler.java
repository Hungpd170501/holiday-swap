package com.example.holidayswap.config;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.example.holidayswap.domain.exception.*;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //400
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public <T extends RuntimeException> ResponseEntity<ApiError> handleValidationException(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Resource not found: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    //401
    @ExceptionHandler({
            VerificationException.class,
            AuthenticationException.class
    })
    public <T extends RuntimeException> ResponseEntity<ApiError> verificationExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Verification exception: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public <T extends RuntimeException> ResponseEntity<ApiError> badCredentialsExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(INCORRECT_EMAIL_OR_PASSWORD)
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Bad credentials exception: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler({JwtException.class})
    public <T extends RuntimeException> ResponseEntity<ApiError> jwtExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(JWT_TOKEN_INVALID)
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("JWT exception: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }


    //403
    @ExceptionHandler({AccessDeniedException.class})
    public <T extends RuntimeException> ResponseEntity<ApiError> accessDeniedExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Access denied exception: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    //404
    @ExceptionHandler({
            ResourceNotFoundException.class,
            EntityNotFoundException.class,
    })
    public <T extends RuntimeException> ResponseEntity<ApiError> resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Resource not found: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    //409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.warn("Data integrity violation exception: %s".formatted(message));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(SERVICE_UNAVAILABLE)
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("Exception: %s".formatted(message));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    //503
    @ExceptionHandler({
            AmazonServiceException.class,
            SdkClientException.class,
            AmazonClientException.class,
    })
    public ResponseEntity<ApiError> awsExceptionHandler(Exception ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .message(SERVICE_UNAVAILABLE)
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        log.error("AWS Exception: %s".formatted(message));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(message);
    }
}