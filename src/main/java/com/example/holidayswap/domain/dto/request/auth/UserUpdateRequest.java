package com.example.holidayswap.domain.dto.request.auth;

import com.example.holidayswap.domain.entity.auth.Gender;
import com.example.holidayswap.domain.entity.auth.UserStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private MultipartFile avatar;
    private String email;
    private String fullName;
    private Gender gender;
    private LocalDate dob;
    private String phone;
    private boolean emailVerified;
    private boolean phoneVerified;
    private UserStatus status;
    private Long roleId;
}
