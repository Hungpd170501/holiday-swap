package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.service.property.PropertyImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/propertyImages")
public class PropertyImagesController {
    private final PropertyImageService propertyImageService;

    @GetMapping
    public ResponseEntity<List<PropertyImageResponse>> gets(
            @RequestParam Long contractId) {
        var propertyImageResponses = propertyImageService.gets(contractId);
        return ResponseEntity.ok(propertyImageResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyImageResponse> get(
            @PathVariable("id") Long id) {
        var propertyImageResponses = propertyImageService.get(id);
        return ResponseEntity.ok(propertyImageResponses);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyImageResponse> create(
            @PathVariable Long id,
            @RequestPart MultipartFile contractImage) {
        var propertyImageCreated = propertyImageService.create(id, contractImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyImageCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(propertyImageCreated);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart MultipartFile contractImage) {
        propertyImageService.update(id, contractImage);
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
