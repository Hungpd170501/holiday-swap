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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resort-amenities")
public class ResortAmenitiesController {
    private final ResortAmenityService resortAmenityService;

    @GetMapping
    public ResponseEntity<Page<ResortAmenityResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(resortAmenityService.gets(name, pageable));
    }


    @GetMapping("/{resortAmenityId}")
    public ResponseEntity<ResortAmenityResponse> get(
            @PathVariable("resortAmenityId") Long resortAmenityId) {
        return ResponseEntity.ok(resortAmenityService.get(resortAmenityId));
    }

    @PostMapping
    public ResponseEntity<ResortAmenityResponse> create(
            @RequestPart(name = "resortAmenity") ResortAmenityRequest dtoRequest,
            @RequestPart(name = "inRoomAmenityIcon") MultipartFile resortAmenityIcon) {
        var dtoResponse = resortAmenityService.create(dtoRequest, resortAmenityIcon);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{resortAmenityId}")
    public ResponseEntity<ResortAmenityResponse> update(@PathVariable("resortAmenityId") Long resortAmenityId,
                                                        @RequestPart(name = "resortAmenity") ResortAmenityRequest dtoRequest,
                                                        @RequestPart(name = "inRoomAmenityIcon", required = false) MultipartFile resortAmenityIcon) {
        var dtoResponse = resortAmenityService.update(resortAmenityId, dtoRequest, resortAmenityIcon);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortAmenityId)
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @DeleteMapping("/{resortAmenityId}")
    public ResponseEntity<Void> delete(@PathVariable("resortAmenityId") Long resortAmenityId) {
        resortAmenityService.delete(resortAmenityId);
        return ResponseEntity.noContent().build();
    }
}
