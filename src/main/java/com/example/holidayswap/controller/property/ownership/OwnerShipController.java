package com.example.holidayswap.controller.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.service.property.ownership.OwnerShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ownership")
public class OwnerShipController {
    private final OwnerShipService ownerShipService;

//    @GetMapping("/property")
//    public ResponseEntity<?> getListByPropertyId(@RequestParam Long propertyId) {
//        return ResponseEntity.ok(ownerShipService.getListByPropertyId(propertyId));
//    }

//    @GetMapping("/user")
//    public ResponseEntity<?> getListByUserId(@RequestParam Long userId) {
//        return ResponseEntity.ok(ownerShipService.getListByUserId(userId));
//    }

//    @GetMapping
//    public ResponseEntity<?> get(@RequestParam Long propertyId,
//                                 @RequestParam Long userId) {
//        return ResponseEntity.ok(ownerShipService.get(propertyId, userId));
//    }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart Long propertyId,
                                    @RequestPart Long userId,
                                    @RequestPart OwnershipRequest dtoRequest,
                                    @RequestPart List<MultipartFile> contractImages) {
        try {
            return ResponseEntity.ok(ownerShipService.create(propertyId, userId, dtoRequest, contractImages));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/simple")
    public ResponseEntity<?> create(@RequestParam Long propertyId,
                                    @RequestParam Long userId,
                                    @RequestBody OwnershipRequest dtoRequest) {
        return ResponseEntity.ok(ownerShipService.create12312(propertyId, userId, dtoRequest));
    }

}
