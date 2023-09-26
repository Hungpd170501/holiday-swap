package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.dto.response.property.amenity.ContractImageResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PropertyContractResponse {
    private Long id;
    //    private OffsetDateTime endPeriod;
    private Date endTime;
    private Date startTime;
    private boolean isDeleted;
    private Long propertyId;
    private List<ContractImageResponse> contractImages;
}
