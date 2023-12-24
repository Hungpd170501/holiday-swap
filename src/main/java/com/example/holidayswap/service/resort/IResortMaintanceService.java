package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.entity.resort.ResortStatus;

import java.time.LocalDateTime;

public interface IResortMaintanceService {
    void CreateResortMaintance(Long resortId, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus);

    void ChangeStatusResortAtStartDate(Long resortId, LocalDateTime startDate);

    void ChangeStatusResortAtEndDate(Long resortId, LocalDateTime endDate);
}
