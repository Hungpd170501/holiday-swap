package com.example.holidayswap.domain.dto.response.property.ownership;

import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.ContractType;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OwnershipResponse {
    private OwnershipId id;
    private Date endTime;
    private Date startTime;
    private ContractType type;
    private ContractStatus status;
    private boolean isDeleted;
    private List<ContractImageResponse> contractImages;
}
