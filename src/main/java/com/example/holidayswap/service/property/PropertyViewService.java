package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyViewRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropertyViewService {
    Page<PropertyViewResponse> gets(String name, Pageable pageable);

    PropertyViewResponse get(Long id);

    PropertyViewResponse create(PropertyViewRequest dtoRequest);

    PropertyViewResponse update(Long id, PropertyViewRequest dtoRequest);

    void delete(Long id);
}
