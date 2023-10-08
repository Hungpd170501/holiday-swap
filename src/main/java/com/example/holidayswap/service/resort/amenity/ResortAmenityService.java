package com.example.holidayswap.service.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResortAmenityService {
    Page<ResortAmenityResponse> gets(String name, Pageable pageable);

    Page<ResortAmenityResponse> gets(Long amenityTypeId, Pageable pageable);

    List<ResortAmenityResponse> gets(Long amenityTypeId);

    List<ResortAmenityResponse> gets(Long amenityTypeId, Long resortId);

    ResortAmenityResponse get(Long id);

    ResortAmenityResponse create(ResortAmenityRequest dtoRequest, MultipartFile resortAmenityIcon);

    ResortAmenityResponse update(Long id, ResortAmenityRequest dtoRequest, MultipartFile resortAmenityIcon);

    void delete(Long id);
}
