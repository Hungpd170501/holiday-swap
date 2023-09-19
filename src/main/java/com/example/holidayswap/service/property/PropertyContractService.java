package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyContractRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyContractResponse;
import com.example.holidayswap.domain.entity.property.PropertyContract;

import java.util.List;

public interface PropertyContractService {
    List<PropertyContractResponse> gets(Long propertyId);

    PropertyContractResponse get(Long id);

    PropertyContract create(Long propertyId, PropertyContractRequest propertyContractRequest);

    PropertyContract update(Long Id, PropertyContractRequest propertyContractRequest);

    void delete(Long id);
}
