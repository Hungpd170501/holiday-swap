package com.example.holidayswap.domain.dto.request.property.coOwner;

import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CoOwnerUpdateRequest {
    private Long propertyId;
    private String roomId;
    private CoOwnerMaintenanceStatus resortStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
