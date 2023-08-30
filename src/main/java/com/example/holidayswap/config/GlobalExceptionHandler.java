package com.example.holidayswap.config;

import com.example.holidayswap.domain.exception.*;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static com.example.holidayswap.constants.ErrorMessage.INCORRECT_EMAIL_OR_PASSWORD;
import static com.example.holidayswap.constants.ErrorMessage.JWT_TOKEN_INVALID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    //400
    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    public <T extends RuntimeException> ResponseEntity<ApiError> resourceNotFoundExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    //401
    @ExceptionHandler({VerificationException.class, AuthenticationException.class})
    public <T extends RuntimeException> ResponseEntity<ApiError> verificationExceptionHandler(T ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
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
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
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
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    //500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiError message = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}