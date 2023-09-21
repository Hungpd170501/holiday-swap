package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyImageService {
    List<PropertyImageResponse> gets(Long propertyId);

    PropertyImageResponse get(Long id);

    PropertyImageResponse create(Long propertyId, MultipartFile multipartFile);

    PropertyImageResponse update(Long Id, MultipartFile multipartFile);

    void delete(Long id);
}
