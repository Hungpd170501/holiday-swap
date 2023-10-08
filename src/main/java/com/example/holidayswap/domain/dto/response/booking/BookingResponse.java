package com.example.holidayswap.domain.dto.response.booking;

import lombok.Data;

@Data
public class BookingResponse {
    private String resortName;
    private String resortAddress;
    private String propertyName;
    private String roomId;
    private int numberOfGuest;
    private String checkInDate;
    private String checkOutDate;
    private String bookingStatus;
    private Double total;
}
