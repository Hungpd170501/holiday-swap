package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyContractRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyContractResponse;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyContractService {
    List<PropertyContractResponse> gets();

    PropertyContractResponse get(Long id);

    PropertyImage create(Long idProperty, MultipartFile multipartFile);

    PropertyImage update(Long Id, Long idProperty, PropertyContractRequest propertyImage);

    void create(Long id);
}
