package com.example.holidayswap.service.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResortAmenityTypeService {
    Page<ResortAmenityTypeResponse> gets(String name, Pageable pageable);

    List<ResortAmenityTypeResponse> gets(Long resortId);

    ResortAmenityTypeResponse get(Long id);

    ResortAmenityTypeResponse create(ResortAmenityTypeRequest dtoRequest);

    ResortAmenityTypeResponse update(Long id, ResortAmenityTypeRequest dtoRequest);

    void delete(Long id);
}
