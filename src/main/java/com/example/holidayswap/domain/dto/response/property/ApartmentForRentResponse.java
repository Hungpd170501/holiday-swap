package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentForRentResponse {
    private CoOwnerId coOwnerId;
    private PropertyResponse property;
    private ResortResponse resort;
    private UserProfileResponse user;
    private AvailableTimeResponse availableTime;
}
