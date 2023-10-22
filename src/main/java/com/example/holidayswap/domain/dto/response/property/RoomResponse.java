package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    CoOwnerResponse coOwner;
    private double min;
    private double max;
    private Date startDate;
    private Date endDate;
    private PropertyResponse property;
    private List<AvailableTimeResponse> availableTimes;
}
