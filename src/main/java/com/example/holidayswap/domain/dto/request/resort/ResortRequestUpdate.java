package com.example.holidayswap.domain.dto.request.resort;

import com.example.holidayswap.domain.entity.resort.ResortStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResortRequestUpdate {
    private Long resortId;
    private ResortStatus resortStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
