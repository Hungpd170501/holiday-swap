package com.example.holidayswap.domain.dto.response.property;

import lombok.Data;

@Data
public class PropertyImageResponse {
    private Long id;
    private Long propertyId;
    private String link;
    private boolean isDeleted;
}
