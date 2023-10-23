package com.example.holidayswap.domain.dto.request.booking;

import lombok.Data;

@Data
public class UserOfBookingRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
}
