package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ContractImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContractImageService {
    List<ContractImageResponse> gets(Long idProperty);

    ContractImageResponse get(Long id);

    ContractImage create(Long contractId, MultipartFile multipartFile);

    ContractImage update(Long Id, Long contractId, ContractImageRequest contractImageRequest);

    void delete(Long id);
}
