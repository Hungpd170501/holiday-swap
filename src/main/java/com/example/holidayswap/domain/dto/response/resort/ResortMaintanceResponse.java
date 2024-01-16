package com.example.holidayswap.domain.dto.response.resort;

import com.example.holidayswap.domain.entity.resort.ResortStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResortMaintanceResponse {
    private Long id;
    private Long resortId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ResortStatus type;
    private List<ResortMaintanceImageResponse> resortMaintanceImage;
}
