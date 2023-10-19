package com.example.holidayswap.controller.property.coOwner;

import com.example.holidayswap.domain.dto.response.property.coOwner.ContractImageResponse;
import com.example.holidayswap.service.property.coOwner.ContractImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/contract-images")
public class ContractImagesController {
    private final ContractImageService contractImageService;

    @GetMapping
    public ResponseEntity<List<ContractImageResponse>> gets(
            @RequestParam Long propertyId,
            @RequestParam Long userId,
            @RequestParam String roomId
    ) {
        var inRoomAmenityResponses = contractImageService.gets(propertyId, userId, roomId);
        return ResponseEntity.ok(inRoomAmenityResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractImageResponse> get(
            @PathVariable("id") Long id) {
        var inRoomAmenityResponse = contractImageService.get(id);
        return ResponseEntity.ok(inRoomAmenityResponse);
    }
}
