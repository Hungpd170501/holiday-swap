package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.entity.property.coOwner.OwnerShipMaintenance;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface CoOwnerService {
    Page<CoOwnerResponse> gets(Long resortId, Long propertyId, Long userId, String roomId, CoOwnerStatus coOwnerStatus, Pageable pageable);

    Page<CoOwnerResponse> getsByPropertyAndRoomId(Long propertyId, String roomId, Pageable pageable);

    CoOwnerResponse get(Long coOwnerId);

    void create(CoOwnerRequest dtoRequest);

    void create(CoOwnerRequest dtoRequest, List<MultipartFile> propertyImages);

    void update(Long coOwnerId, CoOwnerStatus coOwnerStatus);

    void delete(Long coOwnerId);

    void deleteHard(Long coOwnerId);
    void updateStatus(Long propertyId, String apartmentId , CoOwnerMaintenanceStatus resortStatus, LocalDateTime startDate, LocalDateTime endDate, List<MultipartFile> resortImage) throws MessagingException, IOException;

    List<OwnerShipMaintenance> getListOwnerShipMaintenance(Long propertyId, String apartmentId);
}