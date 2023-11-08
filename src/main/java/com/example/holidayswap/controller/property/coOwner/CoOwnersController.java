package com.example.holidayswap.controller.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.service.property.coOwner.CoOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam(defaultValue = "property_id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(coOwnerService.gets(resortId, propertyId, userId, roomId, coOwnerStatus, pageable));
    }

    @GetMapping("/detail")
    public ResponseEntity<CoOwnerResponse> get(
            @RequestParam Long propertyId,
            @RequestParam Long userId,
            @RequestParam String roomId) {
        return ResponseEntity.ok(coOwnerService.get(propertyId, userId, roomId));
    }

    @PostMapping
    public ResponseEntity<CoOwnerResponse> create(@RequestPart("coOwnerId") CoOwnerId coOwnerId,
                                                  @RequestPart("coOwner") CoOwnerRequest dtoRequest,
                                                  @RequestPart List<MultipartFile> contractImages) {
        return ResponseEntity.ok(coOwnerService.create(coOwnerId, dtoRequest, contractImages));
    }

    @PutMapping("/status")
    public ResponseEntity<CoOwnerResponse> updateStatus(
            @RequestParam("propertyId") Long propertyId,
            @RequestParam("userId") Long userId,
            @RequestParam("roomId") String roomId,
            @RequestParam("coOwnerStatus") CoOwnerStatus coOwnerStatus) {
        CoOwnerId coOwnerId = new CoOwnerId(propertyId, userId, roomId);
        return ResponseEntity.ok(coOwnerService.update(coOwnerId, coOwnerStatus));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestPart("coOwnerId") CoOwnerId coOwnerId) {

        coOwnerService.delete(coOwnerId);
        return ResponseEntity.noContent().build();
    }
}
