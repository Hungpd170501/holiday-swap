package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
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
    private AvailableTimeResponse availableTime;
}
