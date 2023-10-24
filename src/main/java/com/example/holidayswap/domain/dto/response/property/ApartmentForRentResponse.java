package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentForRentResponse {
    private PropertyResponse property;
    private ResortResponse resort;
    private UserProfileResponse user;
    private AvailableTimeResponse availableTime;
}
