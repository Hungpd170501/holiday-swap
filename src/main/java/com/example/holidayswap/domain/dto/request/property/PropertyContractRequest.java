package com.example.holidayswap.domain.dto.request.property;

import com.example.holidayswap.domain.entity.property.PropertyContractType;
import lombok.Data;

import java.util.Date;

@Data
public class PropertyContractRequest {
    //    private OffsetDateTime endPeriod;
    private Date endTime;
    private Date startTime;
    private PropertyContractType propertyContractType;
}
