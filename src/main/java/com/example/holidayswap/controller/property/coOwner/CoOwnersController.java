package com.example.holidayswap.controller.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.service.property.coOwner.CoOwnerService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/co-owners")
public class CoOwnersController {
    private final CoOwnerService coOwnerService;


    @GetMapping
    public ResponseEntity<Page<CoOwnerResponse>> get(
            @RequestParam(required = false) Long resortId,
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String roomId,
            @RequestParam(required = false) CoOwnerStatus coOwnerStatus,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "create_date") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(coOwnerService.gets(resortId, propertyId, userId, roomId, coOwnerStatus, pageable));
    }

    @GetMapping("/{coOwnerId}")
    public ResponseEntity<CoOwnerResponse> get(
            @PathVariable Long coOwnerId) {
        return ResponseEntity.ok(coOwnerService.get(coOwnerId));
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestPart @Valid CoOwnerRequest dtoRequest,
            @RequestPart List<MultipartFile> contractImages
    ) {
        if (contractImages.isEmpty()) throw new DataIntegrityViolationException("Contract image can not be empty!.");
        coOwnerService.create(dtoRequest, contractImages);
        return ResponseEntity.ok().build();
    }

    //
    @PutMapping("/{coOwnerId}")
    public ResponseEntity<CoOwnerResponse> updateStatus(
            @PathVariable("coOwnerId") Long coOwnerId,
            @RequestParam("coOwnerStatus") CoOwnerStatus coOwnerStatus) {
        coOwnerService.update(coOwnerId, coOwnerStatus);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-status")
    public ResponseEntity<CoOwnerResponse> updateStatus(
            @RequestPart CoOwnerUpdateRequest resortUpdateRequest,
            @RequestPart(required = false) List<MultipartFile> resortImage) throws MessagingException, IOException {
        coOwnerService.updateStatus(resortUpdateRequest.getPropertyId(), resortUpdateRequest.getRoomId(), resortUpdateRequest.getResortStatus(), resortUpdateRequest.getStartDate(), resortUpdateRequest.getEndDate(), resortImage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getListApartmentMaintain/{propertyId}/{apartmentId}")
    public ResponseEntity<?> getListApartmentMaintain(@PathVariable("propertyId") Long propertyId,
                                                      @PathVariable("apartmentId") String apartmentId) {
        return ResponseEntity.ok(coOwnerService.getListOwnerShipMaintenance(propertyId, apartmentId));
    }
}
