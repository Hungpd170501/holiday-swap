package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.response.property.coOwner.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.coOwner.ContractImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContractImageService {
    List<ContractImageResponse> gets(Long coOwnerId);

    ContractImageResponse get(Long id);

    ContractImageResponse create(Long coOwnerId, MultipartFile multipartFile);

    void delete(Long id);

    void appendToCo(Long coOwnerId, ContractImage image);
}
