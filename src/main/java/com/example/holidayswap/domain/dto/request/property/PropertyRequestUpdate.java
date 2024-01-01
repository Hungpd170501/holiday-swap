package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PropertyRequestUpdate {
    private Long propertyId;
    private PropertyStatus resortStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}