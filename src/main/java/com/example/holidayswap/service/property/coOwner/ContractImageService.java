package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.ContractImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContractImageService {
    List<ContractImageResponse> gets(Long propertyId, Long userId, String roomId);

    ContractImageResponse get(Long id);

    ContractImageResponse create(ContractImageRequest dtoRequest, MultipartFile multipartFile);

    ContractImageResponse update(Long id, MultipartFile multipartFile);

    void delete(Long id);
}
