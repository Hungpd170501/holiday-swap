package com.example.holidayswap.domain.dto.request.auth;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}