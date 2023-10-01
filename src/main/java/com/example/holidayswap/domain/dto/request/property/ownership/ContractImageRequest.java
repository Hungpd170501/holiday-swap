package com.example.holidayswap.domain.dto.request.property.ownership;

import lombok.Data;

@Data
public class ContractImageRequest {
    private Long propertyId;
    private Long userId;
    private Long roomId;
}
