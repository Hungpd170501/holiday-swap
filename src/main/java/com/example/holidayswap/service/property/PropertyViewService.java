package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyViewRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyViewService {
    Page<PropertyViewResponse> gets(String name, Pageable pageable);

    List<PropertyViewResponse> gets();
    PropertyViewResponse get(Long id);


    PropertyViewResponse create(PropertyViewRequest dtoRequest);

    PropertyViewResponse update(Long id, PropertyViewRequest dtoRequest);

    void delete(Long id);
}
