package com.example.holidayswap.domain.dto.response.property;

import lombok.Data;

@Data
public class PropertyViewResponse {
    private Long id;
    private String propertyViewName;
    private String propertyViewDescription;
    private boolean isDeleted;
}