package com.example.holidayswap.service.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ownership.ContractImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContractImageService {
    List<ContractImageResponse> gets(Long propertyId, Long userId);

    ContractImageResponse get(Long id);

    ContractImage createAndReturnObject(ContractImageRequest dtoRequest, MultipartFile multipartFile);
    ContractImageResponse create(ContractImageRequest dtoRequest, MultipartFile multipartFile);

    ContractImageResponse update(Long id, MultipartFile multipartFile);

    void delete(Long id);
}
