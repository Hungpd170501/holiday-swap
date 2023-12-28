package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.entity.resort.ResortStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface IPropertyMaintenanceService {
    List<String> CreatePropertyMaintance(Long resortId, LocalDateTime startDate, LocalDateTime endDate, ResortStatus resortStatus, List<MultipartFile> resortImage);

    void DeactivePropertyAtStartDate(LocalDateTime startDate);
}
