package com.example.holidayswap.domain.dto.response.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CoOwnerResponse {
    private CoOwnerId id;
    private Date endTime;
    private Date startTime;
    private ContractType type;
    private CoOwnerStatus status;
    private boolean isDeleted;
    private List<ContractImageResponse> contractImages;
}
