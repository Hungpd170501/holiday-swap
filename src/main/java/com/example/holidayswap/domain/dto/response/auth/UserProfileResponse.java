package com.example.holidayswap.domain.dto.response.auth;

import com.example.holidayswap.domain.entity.auth.Gender;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Data
public class UserProfileResponse {
    private Long userId;
    private String email;
    private String username;
    private Gender gender;
    private LocalDate dob;
    private String phone;
    @JsonProperty("email_verified")
    private boolean emailVerified;
    @JsonProperty("phone_verified")
    private boolean phoneVerified;
    private UserStatus status;
}
