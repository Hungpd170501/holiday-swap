package com.example.holidayswap.controller.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.service.resort.amenity.ResortAmenityService;
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
@RequestMapping("api/v1/ResortAmenities")
public class ResortAmenitiesController {
    private final ResortAmenityService resortAmenityService;

    @GetMapping("/search")
    public ResponseEntity<Page<ResortAmenityResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(resortAmenityService.gets(name, pageable));
    }

    @GetMapping("/amenityType")
    public ResponseEntity<Page<ResortAmenityResponse>> gets(
            @RequestParam(defaultValue = "") Long amenityId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(resortAmenityService.gets(amenityId, pageable));
    }

    @GetMapping("/amenityType/list")
    public ResponseEntity<List<ResortAmenityResponse>> gets(
            @RequestParam Long amenityId) {
        return ResponseEntity.ok(resortAmenityService.gets(amenityId));
    }

    @GetMapping("/amenityType/resort")
    public ResponseEntity<List<ResortAmenityResponse>> gets(
            @RequestParam Long amenityId,
            @RequestParam Long resortId) {
        return ResponseEntity.ok(resortAmenityService.gets(amenityId, resortId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResortAmenityResponse> get(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(resortAmenityService.get(id));
    }

    @PostMapping
    public ResponseEntity<ResortAmenityResponse> create(
            @RequestBody ResortAmenityRequest dtoRequest) {
        var dtoResponse = resortAmenityService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody ResortAmenityRequest dtoRequest) {
        resortAmenityService.update(id, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resortAmenityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
