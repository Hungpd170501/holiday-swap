package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.amenity.ContractImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContractImageService {
    List<ContractImageResponse> gets(Long contractId);

    ContractImageResponse get(Long id);

    ContractImageResponse create(Long contractId, MultipartFile multipartFile);

    ContractImageResponse update(Long id, MultipartFile multipartFile);

    void delete(Long id);
}
