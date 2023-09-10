package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyService {
    Page<PropertyResponse> getListProperty(Long resortId, Pageable pageable);

    PropertyResponse getProperty(Long propertyId);

    void createProperty(PropertyRequest propertyRequest);

    void updateProperty(Long propertyId, PropertyRequest propertyRequest);

    void deleteProperty(Long propertyId);

    void acceptProperty(Long propertyId);

    void decline(Long propertyId);
}
