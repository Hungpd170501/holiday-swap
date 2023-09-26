package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyTypeRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyTypeService {
    Page<PropertyTypeResponse> gets(String name, Pageable pageable);

    PropertyTypeResponse get(Long id);

    PropertyTypeResponse create(PropertyTypeRequest dtoRequest);

    PropertyTypeResponse update(Long id, PropertyTypeRequest dtoRequest);

    void delete(Long id);
}
