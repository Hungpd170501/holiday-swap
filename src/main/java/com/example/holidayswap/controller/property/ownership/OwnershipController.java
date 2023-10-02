package com.example.holidayswap.controller.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.OwnershipResponse;
import com.example.holidayswap.service.property.ownership.OwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ownerships")
public class OwnershipController {
    private final OwnershipService ownershipService;

    @GetMapping("/properties")
    public ResponseEntity<List<OwnershipResponse>> getListByPropertyId(@RequestParam Long propertyId) {
        return ResponseEntity.ok(ownershipService.getListByPropertyId(propertyId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<OwnershipResponse>> getListByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(ownershipService.getListByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<OwnershipResponse> get(
            @RequestParam Long propertyId, @RequestParam Long userId, @RequestParam String roomId) {
        return ResponseEntity.ok(ownershipService.get(propertyId, userId, roomId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart Long propertyId,
                                    @RequestPart Long userId,
                                    @RequestPart OwnershipRequest dtoRequest,
                                    @RequestPart List<MultipartFile> contractImages
    ) {
        return ResponseEntity.ok(ownershipService.create(propertyId, userId, dtoRequest, contractImages));

    }
}
