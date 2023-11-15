package com.example.holidayswap.domain.dto.response.booking;

import com.example.holidayswap.domain.dto.request.booking.UserOfBookingRequest;
import com.example.holidayswap.domain.entity.booking.UserOfBooking;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HistoryBookingDetailResponse {
    private String resortName;
    private String propertyName;
    private String roomId;
    private String ownerEmail;
    private Date dateCheckIn;
    private Date dateCheckOut;
    private int numberOfGuest;
    private String status;
    private List<UserOfBooking> userOfBooking;
    private Double price;
    private Long availableTimeId;
    private String propertyImage;
    private boolean isRating;

}
