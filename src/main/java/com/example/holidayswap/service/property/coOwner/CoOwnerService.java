package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CoOwnerService {
    List<CoOwnerResponse> getListByPropertyId(Long propertyId);

    List<CoOwnerResponse> getListByUserId(Long userId);

    CoOwnerResponse get(Long propertyId, Long userId, String roomId);

    CoOwnerResponse create(CoOwnerId coOwnerId, CoOwnerRequest dtoRequest);

    CoOwnerResponse create(CoOwnerId coOwnerId, CoOwnerRequest dtoRequest, List<MultipartFile> propertyImages);

    CoOwnerResponse update(CoOwnerId coOwnerId, CoOwnerStatus coOwnerStatus);
//    CoOwnerResponse update(Long propertyId, Long userId, String roomId, CoOwnerRequest dtoRequest);

    void delete(CoOwnerId coOwnerId);
}