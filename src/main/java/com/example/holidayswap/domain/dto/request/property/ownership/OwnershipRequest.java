package com.example.holidayswap.domain.dto.request.property.ownership;

import com.example.holidayswap.domain.entity.property.ownership.ContractType;
import lombok.Data;

import java.util.Date;

@Data
public class OwnershipRequest {
    //    private OwnershipId id;
    private Date endTime;
    private Date startTime;
    private ContractType type;

}
