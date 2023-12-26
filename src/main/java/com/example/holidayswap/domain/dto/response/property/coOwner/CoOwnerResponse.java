package com.example.holidayswap.domain.dto.response.property.coOwner;

import com.example.holidayswap.domain.dto.response.auth.UserProfileResponse;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.ContractType;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CoOwnerResponse {
    private Long id;
    private String roomId;
    private Date endTime;
    private Date startTime;
    private ContractType type;
    private CoOwnerStatus status;
    private boolean isDeleted;
    private List<ContractImageResponse> contractImages;
    private List<TimeFrameResponse> timeFrames;
    private Date createDate;
    private PropertyResponse property;
    private UserProfileResponse user;
}
