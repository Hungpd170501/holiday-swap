package com.example.holidayswap.domain.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class HistoryBookingResponse {
    private long bookingId;
    private Date checkInDate;
    private Date checkOutDate;
    private String propertyName;
    private String roomId;
    private String resortName;
    private String status;
    private Double price;
    private String propertyImage;
    //    private boolean isRating;
    private Long availableTimeId;
}
