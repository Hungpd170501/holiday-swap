package com.example.holidayswap.domain.dto.response.booking;

import com.example.holidayswap.domain.entity.booking.UserOfBooking;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class HistoryDetailBookingOwnerResponse {
    private String resortName;
    private String propertyName;
    private String roomId;
    private String memberBookingEmail;
    private Date dateCheckIn;
    private Date dateCheckOut;
    private int numberOfGuest;
    private String status;
    private List<UserOfBooking> userOfBooking;
    private Double price;
    private String commission;
    private Double total;
    private Long availableTimeId;
    private String propertyImage;
    private String createdDate;
    private boolean canCancel;
    private String userNameBooking;
    private String userNameOwner;
}
