package com.example.holidayswap.domain.dto.response.property.timeFrame;

import lombok.Data;

@Data
public class TimeFrameResponse {
    private Long id;
    private int weekNumber;
    private boolean isDeleted;
    private Long coOwnerId;
}
