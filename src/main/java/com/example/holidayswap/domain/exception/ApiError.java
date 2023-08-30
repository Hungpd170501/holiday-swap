package com.example.holidayswap.domain.exception;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError extends RuntimeException{
    private HttpStatus status;
    private String message;
    private String description;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}