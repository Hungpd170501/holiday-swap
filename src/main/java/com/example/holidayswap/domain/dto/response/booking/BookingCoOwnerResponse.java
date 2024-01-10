package com.example.holidayswap.domain.dto.response.booking;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookingCoOwnerResponse {
    private Long id;

    private String uuid;

    private String qrcode;

    private Long availableTimeId;

    private AvailableTimeResponse availableTime;


    private Long userBookingId;

    private Long ownerId;

    private Long totalDays;

    private UserProfileResponse user;

    private UserProfileResponse userOwner;

    private Double price;

    private Date checkInDate;

    private Date checkOutDate;

    private Double commission;

    private Double actualPrice;

    private String dateBooking;


    private int totalMember;

    private Boolean statusCheckReturn;

//    private Set<UserOfBooking> userOfBookings;

    private EnumBookingStatus.BookingStatus status;

    private EnumBookingStatus.TransferStatus transferStatus;

//    private Set<IssueBooking> issueBookings;

    private RatingResponse rating;

    private EnumBookingStatus.TypeOfBooking typeOfBooking;
}
