package com.example.holidayswap.domain.dto.response.auth;

import com.example.holidayswap.domain.entity.auth.Gender;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JsonIgnoreProperties({"status"})
    private RoleResponse role;
    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime lastModifiedOn;
    private String lastModifiedBy;
}
