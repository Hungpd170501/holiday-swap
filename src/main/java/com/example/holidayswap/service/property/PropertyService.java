package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyService {
    Page<PropertyResponse> getProperties(Pageable pageable);

    PropertyResponse getProperty(Long id);

    PropertyResponse createProperty(PropertyRegisterRequest propertyRegisterRequest);

    PropertyResponse updateProperty(Long id, PropertyUpdateRequest propertyUpdateRequest);

    void deleteProperty(Long id);

}
