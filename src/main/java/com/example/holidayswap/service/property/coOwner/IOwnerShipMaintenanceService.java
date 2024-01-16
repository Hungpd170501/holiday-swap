package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface IOwnerShipMaintenanceService {
    List<String>  CreateOwnerShipMaintenance(Long propertyId, String roomId, LocalDateTime startDate, LocalDateTime endDate, CoOwnerMaintenanceStatus resortStatus, List<MultipartFile> resortImage);
}
