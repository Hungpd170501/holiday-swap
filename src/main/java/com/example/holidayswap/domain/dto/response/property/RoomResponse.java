package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    CoOwnerId coOwnerId;
    private double pricePerNight;
    private PropertyResponse property;
    private List<AvailableTimeResponse> availableTimes;
}
