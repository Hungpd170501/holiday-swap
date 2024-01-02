package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PropertyService {
    Page<PropertyResponse> gets(Long[] resortId, String propertyName, PropertyStatus[] propertyStatus,

                                Pageable pageable);

    PropertyResponse get(Long id);

    List<PropertyResponse> getListPropertyActive(Long resortId);

    List<PropertyResponse> getByResortId(Long resortId);

    PropertyResponse create(PropertyRegisterRequest dtoRequest, List<MultipartFile> propertyImages);

    PropertyResponse create(PropertyRegisterRequest dtoRequest);

    void update(Long id, PropertyUpdateRequest dtoRequest, List<MultipartFile> propertyImages);

    void update(Long id, PropertyStatus propertyStatus);

    void delete(Long id, LocalDate startDate);
    void updateStatus(Long id, PropertyStatus resortStatus, LocalDateTime startDate, LocalDateTime endDate, List<MultipartFile> resortImage) throws MessagingException, IOException;
}
