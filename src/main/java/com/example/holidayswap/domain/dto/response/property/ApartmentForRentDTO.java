package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ApartmentForRentDTO {
    CoOwnerId coOwnerId;
    CoOwner coOwner;
    private double pricePerNight;
    private Property property;
    private List<AvailableTime> availableTimes;

    public ApartmentForRentDTO(CoOwnerId coOwnerId, CoOwner coOwner, double pricePerNight, Property property) {
        this.coOwnerId = coOwnerId;
        this.coOwner = coOwner;
        this.pricePerNight = pricePerNight;
        this.property = property;
    }
}
