package com.example.holidayswap.controller.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.service.resort.amenity.ResortAmenityService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resort-amenity-types")
public class ResortAmenityTypesController {
    private final ResortAmenityTypeService resortAmenityTypeService;
    private final ResortAmenityService resortAmenityService;

    @GetMapping
    public ResponseEntity<Page<ResortAmenityTypeResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(resortAmenityTypeService.gets(name, pageable));
    }

    @GetMapping("/{amenityTypeId}/resort-amenities")
    public ResponseEntity<Page<ResortAmenityResponse>> getResortAmenities(
            @PathVariable("amenityTypeId") Long amenityTypeId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(resortAmenityService.gets(amenityTypeId, pageable));
    }

    @GetMapping("/{amenityTypeId}")
    public ResponseEntity<ResortAmenityTypeResponse> get(
            @PathVariable("amenityTypeId") Long amenityTypeId) {
        return ResponseEntity.ok(resortAmenityTypeService.get(amenityTypeId));
    }

    @PostMapping
    public ResponseEntity<ResortAmenityTypeResponse> create(
            @RequestBody ResortAmenityTypeRequest dtoRequest) {
        var dtoResponse = resortAmenityTypeService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{amenityTypeId}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{amenityTypeId}")
    public ResponseEntity<ResortAmenityTypeResponse> update(@PathVariable("amenityTypeId") Long amenityTypeId,
                                                            @RequestBody ResortAmenityTypeRequest dtoRequest) {
        var dtoResponse = resortAmenityTypeService.update(amenityTypeId, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{amenityTypeId}")
                .buildAndExpand(amenityTypeId)
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @DeleteMapping("/{amenityTypeId}")
    public ResponseEntity<Void> delete(@PathVariable("amenityTypeId") Long amenityTypeId) {
        resortAmenityTypeService.delete(amenityTypeId);
        return ResponseEntity.noContent().build();
    }
}
