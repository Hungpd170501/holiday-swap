package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRegisterRequest;
import com.example.holidayswap.domain.dto.request.property.PropertyUpdateRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.service.property.PropertyService;
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
@RequestMapping("api/v1/properties")
public class PropertiesController {
    private final PropertyService propertyService;

    @GetMapping("/search")
    public ResponseEntity<Page<PropertyResponse>> getProperties(
            @RequestParam(defaultValue = "") Long resortId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var properties = propertyService.getProperties(pageable);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponse> getProperty(
            @PathVariable("propertyId") Long propertyId) {
        var property = propertyService.getProperty(propertyId);
        return ResponseEntity.ok(property);
    }

    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(@RequestBody PropertyRegisterRequest propertyRegisterRequest) {
        var propertyCreated = propertyService.createProperty(propertyRegisterRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(propertyCreated);
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<Void> updateProperty(@PathVariable Long propertyId,
                                               @RequestBody PropertyUpdateRequest propertyUpdateRequest) {
        propertyService.updateProperty(propertyId, propertyUpdateRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
        return ResponseEntity.noContent().build();
    }

}
