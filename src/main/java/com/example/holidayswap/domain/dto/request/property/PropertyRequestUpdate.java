package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.entity.resort.ResortStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PropertyRequestUpdate {
    private Long propertyId;
    private ResortStatus resortStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}