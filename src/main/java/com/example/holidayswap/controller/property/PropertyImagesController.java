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
@RequestMapping("api/v1/propertyImages")
public class PropertyImagesController {
    private final PropertyImageService propertyImageService;

    @GetMapping("/{id}")
    public ResponseEntity<PropertyImageResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = propertyImageService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping
    public ResponseEntity<PropertyImageResponse> create(
            @RequestPart Long propertyId,
            @RequestPart MultipartFile propertyImage) {
        var dtoResponse = propertyImageService.create(propertyId, propertyImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart MultipartFile propertyImage) {
        propertyImageService.update(id, propertyImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propertyImageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
