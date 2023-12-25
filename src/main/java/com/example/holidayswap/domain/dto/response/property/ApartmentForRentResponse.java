package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApartmentForRentResponse {
    private AvailableTimeResponse availableTime;
    private CoOwnerResponse coOwner;
    private PropertyResponse property;
    private ResortResponse resort;
    private UserProfileResponse user;
    private List<TimeHasBooked> timeHasBooked;
}
