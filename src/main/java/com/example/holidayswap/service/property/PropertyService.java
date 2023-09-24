package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {
    Page<PropertyResponse> gets(Pageable pageable);

    PropertyResponse get(Long id);

    PropertyResponse create(Long userId,
                    PropertyRegisterRequest propertyRegisterRequest,
                    List<MultipartFile> propertyImages,
                    List<MultipartFile> propertyContractImages);

    PropertyResponse update(Long id, PropertyUpdateRequest propertyUpdateRequest);

    void delete(Long id);

}
