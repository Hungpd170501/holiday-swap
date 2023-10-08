package com.example.holidayswap.domain.dto.request.booking;

import lombok.Data;

import java.util.Date;

@Data
public class BookingRequest {
    private Long propertyId;
    private String roomId;
    private Long userId;
    private Date checkInDate;
    private Date checkOutDate;
}
