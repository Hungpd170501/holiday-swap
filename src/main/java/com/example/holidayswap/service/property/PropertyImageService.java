package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyImageRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyImageService {
    List<PropertyImageResponse> gets(Long idProperty);

    PropertyImageResponse get(Long id);

    PropertyImage create(Long idProperty, MultipartFile multipartFile);

    PropertyImage update(Long Id, Long idProperty, PropertyImageRequest propertyImage);

    void delete(Long id);
}
