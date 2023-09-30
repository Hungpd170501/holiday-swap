package com.example.holidayswap.controller.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.service.property.ownership.OwnershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ownership")
public class OwnershipController {
    private final OwnershipService ownershipService;

    @GetMapping("/property")
    public ResponseEntity<?> getListByPropertyId(@RequestParam Long propertyId) {
        return ResponseEntity.ok(ownershipService.getListByPropertyId(propertyId));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getListByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(ownershipService.getListByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long propertyId,
                                 @RequestParam Long userId) {
        return ResponseEntity.ok(ownershipService.get(propertyId, userId));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart Long propertyId,
                                    @RequestPart Long userId,
                                    @RequestPart OwnershipRequest dtoRequest,
                                    @RequestPart List<MultipartFile> contractImages) {
        return ResponseEntity.ok(ownershipService.create(propertyId, userId, dtoRequest, contractImages));

    }

    @PostMapping("/simple")
    public ResponseEntity<?> create(@RequestParam Long propertyId,
                                    @RequestParam Long userId,
                                    @RequestBody OwnershipRequest dtoRequest) {
        return ResponseEntity.ok(ownershipService.create(propertyId, userId, dtoRequest));
    }

}
