package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.service.property.PropertyImageService;
import com.example.holidayswap.service.property.PropertyService;
import com.example.holidayswap.service.property.amenity.InRoomAmenityService;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/properties")
public class PropertiesController {
    private final PropertyService propertyService;
    private final InRoomAmenityTypeService inRoomAmenityTypeService;
    private final InRoomAmenityService inRoomAmenityService;
    private final PropertyImageService propertyImageService;

    @GetMapping
    public ResponseEntity<Page<PropertyResponse>> gets(
            @RequestParam(defaultValue = "") Long resortId,
            @RequestParam(value = "timeCheckIn", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date timeCheckIn,
            @RequestParam(value = "timeCheckOut", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date timeCheckOut,
            @RequestParam(defaultValue = "0") Integer numberGuest,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var properties = propertyService.gets(resortId, timeCheckIn, timeCheckOut, numberGuest, pageable);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{propertyId}/property-in-room-amenity-types")
    public ResponseEntity<List<InRoomAmenityTypeResponse>> getPropertyInRoomAmenityTypes(
            @PathVariable("propertyId") Long propertyId) {
        return ResponseEntity.ok(inRoomAmenityTypeService.gets(propertyId));
    }

    @GetMapping("/{propertyId}/property-image")
    public ResponseEntity<List<PropertyImageResponse>> getPropertyImages(
            @PathVariable("propertyId") Long propertyId) {
        var dtoResponses = propertyImageService.gets(propertyId);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{propertyId}/property-in-room-amenity-types/{propertyInRoomAmenityTypeId}/property-in-room-amenities")
    public ResponseEntity<List<InRoomAmenityResponse>> getPropertyInRoomAmenities(
            @PathVariable("propertyId") Long propertyId,
            @PathVariable("propertyInRoomAmenityTypeId") Long amenityTypeId) {
        return ResponseEntity.ok(inRoomAmenityService.gets(amenityTypeId, propertyId));
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponse> get(@PathVariable("propertyId") Long propertyId) {
        var property = propertyService.get(propertyId);
        return ResponseEntity.ok(property);
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> create(
            @RequestPart Long userId,
            @RequestPart PropertyRegisterRequest propertyRegisterRequest,
            @RequestPart List<MultipartFile> propertyImages) {
        var propertyCreated = propertyService.create(userId,
                propertyRegisterRequest,
                propertyImages);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(propertyService.get(propertyCreated.getId()));
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<Void> update(@PathVariable("propertyId") Long propertyId, @RequestBody PropertyUpdateRequest propertyUpdateRequest) {
        propertyService.update(propertyId, propertyUpdateRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(propertyId).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> delete(@PathVariable("propertyId") Long propertyId) {
        propertyService.delete(propertyId);
        return ResponseEntity.noContent().build();
    }

}
