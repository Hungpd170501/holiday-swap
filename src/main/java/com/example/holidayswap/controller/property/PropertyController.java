package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.PropertyRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyResponse;
import com.example.holidayswap.service.property.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/property")
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping("/search")
    public ResponseEntity<Page<PropertyResponse>> getProperties(
            @RequestParam(defaultValue = "") Long resortId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var properties = propertyService.getListProperty(resortId, pageable);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyResponse> getProperty(
            @PathVariable("propertyId") Long propertyId) {
        return ResponseEntity.ok(propertyService.getProperty(propertyId));
    }

    @PostMapping
    public ResponseEntity<Void> createProperty(@RequestBody PropertyRequest propertyRequest) {
        propertyService.createProperty(propertyRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<Void> updateProperty(@PathVariable Long propertyId,
                                               @RequestBody PropertyRequest propertyRequest) {
        propertyService.updateProperty(propertyId, propertyRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId) {
        propertyService.deleteProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}
