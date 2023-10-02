package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface PropertyService {
    Page<PropertyResponse> gets(Long resortId, Date timeCheckIn, Date timeCheckOut, Pageable pageable);

    PropertyResponse get(Long id);

    List<PropertyResponse> getByResortId(Long resortId);

    PropertyResponse create(Long userId,
                            PropertyRegisterRequest dtoRequest,
                            List<MultipartFile> propertyImages);

    PropertyResponse create(Long userId, PropertyRegisterRequest dtoRequest);

    PropertyResponse update(Long id, PropertyUpdateRequest dtoRequest);

    void delete(Long id);

}
