package com.example.holidayswap.domain.dto.request.auth;

import com.example.holidayswap.domain.entity.auth.Gender;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserProfileUpdateRequest {
    private MultipartFile avatar;
    private String fullName;
    private Gender gender;
    private LocalDate dob;
}
