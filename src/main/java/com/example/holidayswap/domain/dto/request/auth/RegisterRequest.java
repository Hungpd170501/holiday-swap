package com.example.holidayswap.domain.dto.request.auth;

import com.example.holidayswap.domain.entity.auth.Gender;
import com.example.holidayswap.domain.entity.auth.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private Gender gender;
    private LocalDate dob;
    private String phone;
    private Role role;
}
