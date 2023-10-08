package com.example.holidayswap.controller.property.coOwner;

import com.example.holidayswap.domain.dto.request.property.coOwner.CoOwnerRequest;
import com.example.holidayswap.domain.dto.response.property.coOwner.CoOwnerResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
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
    public ResponseEntity<?> create(@RequestPart CoOwnerId coOwnerId,
                                    @RequestPart("CoOwner") CoOwnerRequest dtoRequest,
                                    @RequestPart List<MultipartFile> contractImages
    ) {
        return ResponseEntity.ok(ownershipService.create(coOwnerId, dtoRequest, contractImages));

    }
}
