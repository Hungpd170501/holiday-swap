package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked2;
import com.example.holidayswap.domain.entity.resort.ResortMaintance;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface IResortMaintanceService {
    List<String> CreateResortMaintance(Long resortId, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus, List<MultipartFile> resortImage);

    void ChangeStatusResortAtStartDate(Long resortId, LocalDateTime startDate);

    void ChangeStatusResortAtEndDate(Long resortId, LocalDateTime endDate);
    List<ResortMaintance> getResortMaintanceByResortId(Long resortId);

    void deactiveResort(LocalDateTime now);

    List<TimeHasBooked2> getTimeMaintain(Long resortId);
}
