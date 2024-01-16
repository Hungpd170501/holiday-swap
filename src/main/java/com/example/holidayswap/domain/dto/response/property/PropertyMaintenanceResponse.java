package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.property.PropertyStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PropertyMaintenanceResponse {
    private Long id;
    private Long propertyId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PropertyStatus type;
    private List<PropertyMaintenanceImageResponse> propertyMaintenanceImages;
}
