package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.entity.property.Ownership;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface IOwnerShipService {
    Ownership create(Long propertyId, Date startDate, Date endDate, List<MultipartFile> propertyImages);
}