package com.example.holidayswap.domain.dto.request.auth;

import com.example.holidayswap.domain.entity.auth.Gender;
import com.example.holidayswap.domain.entity.auth.Role;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String email;
    private String password;
    private String username;
    private Gender gender;
    private LocalDate dob;
    private String phone;
    @JsonProperty("email_verified")
    private boolean emailVerified;
    @JsonProperty("phone_verified")
    private boolean phoneVerified;
    private UserStatus status;
    private Role role;
}
