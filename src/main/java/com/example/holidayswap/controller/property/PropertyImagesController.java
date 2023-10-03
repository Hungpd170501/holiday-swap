package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.service.property.PropertyImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/property-images")
public class PropertyImagesController {
    private final PropertyImageService propertyImageService;

    @GetMapping("/{propertyImageId}")
    public ResponseEntity<PropertyImageResponse> get(
            @PathVariable("propertyImageId") Long propertyImageId) {
        var dtoResponse = propertyImageService.get(propertyImageId);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping
    public ResponseEntity<PropertyImageResponse> create(
            @RequestPart("propertyId") Long propertyId,
            @RequestPart MultipartFile propertyImage) {
        var dtoResponse = propertyImageService.create(propertyId, propertyImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{propertyImageId}")
    public ResponseEntity<PropertyImageResponse> update(@PathVariable("propertyImageId") Long propertyImageId,
                                       @RequestPart MultipartFile propertyImage) {
        var dtoResponse = propertyImageService.update(propertyImageId, propertyImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyImageId)
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @DeleteMapping("/{propertyImageId}")
    public ResponseEntity<Void> delete(@PathVariable("propertyImageId") Long propertyImageId) {
        propertyImageService.delete(propertyImageId);
        return ResponseEntity.noContent().build();
    }
}
