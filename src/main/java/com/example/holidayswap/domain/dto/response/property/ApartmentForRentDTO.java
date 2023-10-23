package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentForRentDTO {
    private Property property;
    private AvailableTime availableTime;
}
