package com.example.holidayswap.controller.property.inRoomAmenity;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityResponse;
import com.example.holidayswap.service.property.inRoomAmenityService.InRoomAmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/inRoomAmenities")
public class InRoomAmenitiesController {
    final private InRoomAmenityService inRoomAmenityService;

    @GetMapping("/search")
    public ResponseEntity<Page<InRoomAmenityResponse>> gets(
            @RequestParam(defaultValue = "") String searchName,
            @RequestParam(defaultValue = "") Long inRoomAmenityTypeId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var inRoomAmenityResponses = inRoomAmenityService.gets(searchName, inRoomAmenityTypeId, pageable);
        return ResponseEntity.ok(inRoomAmenityResponses);
    }

    @GetMapping("/property")
    public ResponseEntity<List<InRoomAmenityResponse>> gets(
            @RequestParam Long propertyId) {
        var inRoomAmenityResponses = inRoomAmenityService.gets(propertyId);
        return ResponseEntity.ok(inRoomAmenityResponses);
    }

    @GetMapping("/property/inRoomAmenityType")
    public ResponseEntity<List<InRoomAmenityResponse>> gets(
            @RequestParam Long propertyId,
            @RequestParam Long inRoomAmenityType) {
        var inRoomAmenityResponses = inRoomAmenityService.gets(propertyId, inRoomAmenityType);
        return ResponseEntity.ok(inRoomAmenityResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InRoomAmenityResponse> get(
            @PathVariable("id") Long id) {
        var inRoomAmenityResponse = inRoomAmenityService.get(id);
        return ResponseEntity.ok(inRoomAmenityResponse);
    }

    @PostMapping
    public ResponseEntity<InRoomAmenityResponse> create(
            @RequestBody InRoomAmenityRequest inRoomAmenityRequest) {
        var inRoomAmenityCreated = inRoomAmenityService.create(inRoomAmenityRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inRoomAmenityCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(inRoomAmenityCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody InRoomAmenityRequest inRoomAmenityRequest) {
        inRoomAmenityService.update(id, inRoomAmenityRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inRoomAmenityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
