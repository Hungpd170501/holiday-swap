package com.example.holidayswap.domain.dto.request.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CoOwnerRequest {
    private Date endTime;
    private Date startTime;
    private ContractType type;
    private List<TimeFrameRequest> timeFrames;

}
