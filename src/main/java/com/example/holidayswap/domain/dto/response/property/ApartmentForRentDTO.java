package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwner;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.entity.resort.Resort;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentForRentDTO {
    private AvailableTime availableTime;
    private CoOwner coOwner;
    private Property property;
    private Resort resort;
    private User user;
}
