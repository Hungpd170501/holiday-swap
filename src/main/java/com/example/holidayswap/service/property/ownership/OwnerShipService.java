package com.example.holidayswap.service.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.OwnershipResponse;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OwnerShipService {
//    List<OwnershipResponse> getListByPropertyId(Long propertyId);
//
//    List<OwnershipResponse> getListByUserId(Long userId);
//
//    OwnershipResponse get(Long propertyId, Long userId);

    OwnershipResponse create12312(Long propertyId, Long userId, OwnershipRequest dtoRequest);

    Ownership create(Long propertyId, Long userId, OwnershipRequest dtoRequest, List<MultipartFile> propertyImages);

    OwnershipResponse update(Long propertyId, Long userId);

    void delete(Long propertyId, Long userId);
}