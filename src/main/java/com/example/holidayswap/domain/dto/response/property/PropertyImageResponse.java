package com.example.holidayswap.domain.dto.response.property;

import lombok.Data;

@Data
public class PropertyImageResponse {
    private Long id;
    private String link;
    private boolean isDeleted;
    private Long propertyId;
}
