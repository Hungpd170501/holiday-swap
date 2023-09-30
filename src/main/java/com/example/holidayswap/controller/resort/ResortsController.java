package com.example.holidayswap.controller.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resorts")
public class ResortsController {
    final private ResortService resortService;
    final private ResortImageService resortImageService;
    final private ResortAmenityTypeService resortAmenityTypeService;
    final private ResortAmenityService resortAmenityService;

    @GetMapping
    public ResponseEntity<Page<ResortResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(resortService.gets(name, pageable));
    }

    @GetMapping("/{id}/resort-images")
    public ResponseEntity<List<ResortImageResponse>> getResortImages(
            @PathVariable("id") Long id) {
        var dtoResponses = resortImageService.gets(id);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{id}/resort-amenity-types")
    public ResponseEntity<List<ResortAmenityTypeResponse>> getResortAmenityTypes(
            @PathVariable("id") Long resortId) {
        return ResponseEntity.ok(resortAmenityTypeService.gets(resortId));
    }

    @GetMapping("/{resortId}/resort-amenity-types/{amenityTypeId}/resort-amenities")
    public ResponseEntity<List<ResortAmenityResponse>> gets(
            @PathVariable("resortId") Long resortId,
            @PathVariable("amenityTypeId") Long amenityId) {
        return ResponseEntity.ok(resortAmenityService.gets(amenityId, resortId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResortResponse> get(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(resortService.get(id));
    }

    @PostMapping
    public ResponseEntity<ResortResponse> create(
            @RequestPart ResortRequest resortRequest,
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

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody ResortRequest resortRequest) {
        resortService.update(id, resortRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resortService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
