package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.PropertyTypeRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyTypeResponse;
import com.example.holidayswap.service.property.PropertyTypeService;
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
@RequestMapping("api/v1/property-types")
public class PropertyTypesController {
    private final PropertyTypeService propertyTypeService;

    @GetMapping
    public ResponseEntity<Page<PropertyTypeResponse>> gets(
            @RequestParam(defaultValue = "") String searchName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        var inRoomAmenityTypeResponses = propertyTypeService.gets(searchName, pageable);
        return ResponseEntity.ok(inRoomAmenityTypeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyTypeResponse> get(
            @PathVariable("id") Long id) {
        var inRoomAmenityTypeResponse = propertyTypeService.get(id);
        return ResponseEntity.ok(inRoomAmenityTypeResponse);
    }

    @PostMapping
    public ResponseEntity<PropertyTypeResponse> create(
            @RequestBody PropertyTypeRequest inRoomAmenityTypeRequest) {
        var inRoomAmenityTypeCreated = propertyTypeService.create(inRoomAmenityTypeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inRoomAmenityTypeCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(inRoomAmenityTypeCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody PropertyTypeRequest inRoomAmenityTypeRequest) {
        propertyTypeService.update(id, inRoomAmenityTypeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propertyTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
