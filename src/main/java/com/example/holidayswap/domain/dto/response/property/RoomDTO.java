package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {
    CoOwner coOwner;
    private double min;
    private double max;
    private Date startDate;
    private Date endDate;
    private Property property;
    private List<AvailableTime> availableTimes;

    public RoomDTO(CoOwner coOwner, Property property) {
        this.coOwner = coOwner;
        this.property = property;
    }
}
