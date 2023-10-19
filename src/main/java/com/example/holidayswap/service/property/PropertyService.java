package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface PropertyService {
    Page<PropertyResponse> gets(Long resortId, Date timeCheckIn, Date timeCheckOut, int numberGuests, PropertyStatus propertyStatus, Pageable pageable);

    PropertyResponse get(Long id);

    List<PropertyResponse> getByResortId(Long resortId);

    PropertyResponse create(PropertyRegisterRequest dtoRequest,
                            List<MultipartFile> propertyImages);

    PropertyResponse create(PropertyRegisterRequest dtoRequest);

    PropertyResponse update(Long id, PropertyUpdateRequest dtoRequest);

    PropertyResponse update(Long id, PropertyStatus propertyStatus);
    void delete(Long id);

}
