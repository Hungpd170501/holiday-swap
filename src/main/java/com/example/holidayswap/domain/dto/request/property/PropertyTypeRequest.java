package com.example.holidayswap.domain.dto.request.property;

import lombok.Data;


@Data
public class PropertyTypeRequest {
    private String propertyTypeName;
    private String propertyTypeDescription;
}