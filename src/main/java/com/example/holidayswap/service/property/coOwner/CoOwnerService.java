package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CoOwnerService {
    //get list
    Page<CoOwnerResponse> getByResortId(Long resortId, Pageable pageable);

    Page<CoOwnerResponse> getByPropertyId(Long propertyId, Pageable pageable);

    Page<CoOwnerResponse> getCoOwnerBelongToUser(Long userId, Long propertyId, Pageable pageable);

    Page<ResortResponse> getResortUserHaveOwner(Long userId, Pageable pageable);

    CoOwnerResponse get(Long propertyId, Long userId, String roomId);

    CoOwnerResponse create(CoOwnerId coOwnerId, CoOwnerRequest dtoRequest);

    CoOwnerResponse create(CoOwnerId coOwnerId, CoOwnerRequest dtoRequest, List<MultipartFile> propertyImages);

    CoOwnerResponse update(CoOwnerId coOwnerId, CoOwnerStatus coOwnerStatus);
//    CoOwnerResponse update(Long propertyId, Long userId, String roomId, CoOwnerRequest dtoRequest);

    void delete(CoOwnerId coOwnerId);
}