package com.example.holidayswap.domain.dto.request.auth;

import lombok.Data;

@Data
public class RoleRequest {
    private String name;
    private boolean status;
}
