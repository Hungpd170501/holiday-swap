package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApartmentForRentResponse {
    private CoOwnerId coOwnerId;
    private PropertyResponse property;
    private ResortResponse resort;
    private UserProfileResponse user;
    private AvailableTimeResponse availableTime;
    private List<TimeHasBooked> timeHasBooked;
}
