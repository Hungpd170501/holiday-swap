package com.example.holidayswap.domain.dto.response.property.ownership;

import lombok.Data;

@Data
public class ContractImageResponse {
    private Long id;
    private String link;
    private boolean isDeleted;
    private Long propertyId;
    private Long userId;
}
