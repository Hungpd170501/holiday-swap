package com.example.holidayswap.controller.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerStatus;
import com.example.holidayswap.service.property.coOwner.CoOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/co-owners")
public class CoOwnersController {
    private final CoOwnerService ownershipService;

    @GetMapping("/properties")
    public ResponseEntity<List<CoOwnerResponse>> getListByPropertyId(@RequestParam Long propertyId) {
        return ResponseEntity.ok(ownershipService.getListByPropertyId(propertyId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<CoOwnerResponse>> getListByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(ownershipService.getListByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<CoOwnerResponse> get(
            @RequestParam Long propertyId,
            @RequestParam Long userId,
            @RequestParam String roomId) {
        return ResponseEntity.ok(ownershipService.get(propertyId, userId, roomId));
    }

    @PostMapping
    public ResponseEntity<CoOwnerResponse> create(@RequestPart("coOwnerId") CoOwnerId coOwnerId,
                                                  @RequestPart("coOwner") CoOwnerRequest dtoRequest,
                                                  @RequestPart List<MultipartFile> contractImages) {
        return ResponseEntity.ok(ownershipService.create(coOwnerId, dtoRequest, contractImages));
    }

    @PutMapping
    public ResponseEntity<CoOwnerResponse> update(@RequestPart("coOwnerId") CoOwnerId coOwnerId,
                                                  @RequestPart("coOwnerStatus") CoOwnerStatus coOwnerStatus) {
        return ResponseEntity.ok(ownershipService.update(coOwnerId, coOwnerStatus));
    }

    @PutMapping("/status")
    public ResponseEntity<CoOwnerResponse> updateStatus(
            @RequestPart("coOwnerId") CoOwnerId coOwnerId,
            @RequestPart("coOwnerStatus") CoOwnerStatus coOwnerStatus) {
        return ResponseEntity.ok(ownershipService.update(coOwnerId, coOwnerStatus));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestPart("coOwnerId") CoOwnerId coOwnerId) {

        ownershipService.delete(coOwnerId);
        return ResponseEntity.noContent().build();
    }
}
