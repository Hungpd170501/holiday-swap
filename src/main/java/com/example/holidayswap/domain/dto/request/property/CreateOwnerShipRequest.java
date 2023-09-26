package com.example.holidayswap.domain.dto.request.property;

import lombok.Data;

import java.util.Date;

@Data
public class CreateOwnerShipRequest {
    private Long propertyId;
    private Date startDate;
    private Date endDate;
}
