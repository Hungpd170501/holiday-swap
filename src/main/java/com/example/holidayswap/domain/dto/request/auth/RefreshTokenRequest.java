package com.example.holidayswap.domain.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @JsonProperty("refresh_token")
    private String refreshToken;
}