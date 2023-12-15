package com.example.holidayswap.domain.dto.response.property.rating;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRatingResponse {
    private Long userId;
    private String email;
    private String fullName;
    private String userName;
    private LocalDate dob;
    private String avatar;
}
