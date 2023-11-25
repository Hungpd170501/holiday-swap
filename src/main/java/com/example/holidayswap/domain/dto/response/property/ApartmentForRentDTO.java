package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.entity.resort.Resort;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentForRentDTO {
    private CoOwnerId coOwnerId;
    private Property property;
    private Resort resort;
    private User user;
    private AvailableTime availableTime;
}
