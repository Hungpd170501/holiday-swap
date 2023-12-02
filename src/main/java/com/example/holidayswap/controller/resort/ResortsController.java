package com.example.holidayswap.controller.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.request.resort.ResortUpdateRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.service.resort.ResortImageService;
import com.example.holidayswap.service.resort.ResortService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityTypeService;
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
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resorts")
public class ResortsController {
    private final ResortService resortService;
    private final ResortImageService resortImageService;
    private final ResortAmenityTypeService resortAmenityTypeService;
    private final ResortAmenityService resortAmenityService;

    @GetMapping
    public ResponseEntity<Page<ResortResponse>> gets(
            @RequestParam(value = "locationName", defaultValue = "") String locationName,
            @RequestParam(value = "nameResort", defaultValue = "") String nameResort,
            @RequestParam(value = "resortAmenity", required = false) Set<Long> listOfResortAmenity,
            @RequestParam(value = "resortStatus", required = false) ResortStatus resortStatus,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(resortService.gets(locationName, nameResort, listOfResortAmenity, resortStatus, pageable));
    }

    @GetMapping("/{resortId}/resort-images")
    public ResponseEntity<List<ResortImageResponse>> getResortImages(
            @PathVariable("resortId") Long resortId) {
        var dtoResponses = resortImageService.gets(resortId);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{resortId}/resort-amenity-types")
    public ResponseEntity<List<ResortAmenityTypeResponse>> getResortAmenityTypes(
            @PathVariable("resortId") Long resortId) {
        return ResponseEntity.ok(resortAmenityTypeService.gets(resortId));
    }

    @GetMapping("/{resortId}/resort-amenity-types/{amenityTypeId}/resort-amenities")
    public ResponseEntity<List<ResortAmenityResponse>> gets(
            @PathVariable("resortId") Long resortId,
            @PathVariable("amenityTypeId") Long amenityId) {
        return ResponseEntity.ok(resortAmenityService.gets(amenityId, resortId));
    }

    @GetMapping("/getList")
    public ResponseEntity<List<ResortResponse>> gets() {
        return ResponseEntity.ok(resortService.getsListResortHaveProperty());
    }

    @GetMapping("/{resortId}")
    public ResponseEntity<ResortResponse> get(
            @PathVariable("resortId") Long resortId) {
        return ResponseEntity.ok(resortService.get(resortId));
    }

    @PostMapping
    public ResponseEntity<ResortResponse> create(
            @RequestPart(name = "resort") ResortRequest resortRequest,
            @RequestPart List<MultipartFile> resortImage
    ) {
        var dtoResponse = resortService.create(resortRequest, resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{resortId}")
    public ResponseEntity<Void> update(@PathVariable("resortId") Long resortId,
                                       @RequestPart ResortUpdateRequest resortRequest,
                                       @RequestPart(required = false) List<MultipartFile> resortImage) {
        resortService.update(resortId, resortRequest,resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{resortId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable("resortId") Long resortId,
                                             @RequestBody ResortStatus resortStatus) {
        resortService.update(resortId, resortStatus);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{resortId}")
    public ResponseEntity<Void> delete(@PathVariable("resortId") Long resortId) {
        resortService.delete(resortId);
        return ResponseEntity.noContent().build();
    }
}
