package com.example.holidayswap.controller.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.service.property.amenity.InRoomAmenityService;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeService;
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
@RequestMapping("api/v1/in-room-amenity-types")
public class InRoomAmenityTypesController {
    final private InRoomAmenityTypeService inRoomAmenityTypeService;
    final private InRoomAmenityService inRoomAmenityService;

    @GetMapping
    public ResponseEntity<Page<InRoomAmenityTypeResponse>> gets(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection, @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(inRoomAmenityTypeService.gets(name, pageable));
    }

    @GetMapping("/{amenityTypeId}/property-in-room-amenities")
    public ResponseEntity<Page<InRoomAmenityResponse>> gets(@PathVariable("amenityTypeId") Long amenityTypeId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection, @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(inRoomAmenityService.gets(amenityTypeId, pageable));
    }


    @GetMapping("/list")
    public ResponseEntity<List<InRoomAmenityTypeResponse>> getlist() {
        return ResponseEntity.ok(inRoomAmenityTypeService.gets());
    }

    @GetMapping("/{amenityTypeId}")
    public ResponseEntity<InRoomAmenityTypeResponse> get(@PathVariable("amenityTypeId") Long amenityTypeId) {
        return ResponseEntity.ok(inRoomAmenityTypeService.get(amenityTypeId));
    }

    @PostMapping
    public ResponseEntity<InRoomAmenityTypeResponse> create(@RequestBody InRoomAmenityTypeRequest dtoRequest) {
        var dtoResponse = inRoomAmenityTypeService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{amenityTypeId}")
    public ResponseEntity<InRoomAmenityTypeResponse> update(@PathVariable("amenityTypeId") Long amenityTypeId, @RequestBody InRoomAmenityTypeRequest dtoRequest) {
        var dtoResponse = inRoomAmenityTypeService.update(amenityTypeId, dtoRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(amenityTypeId).toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @DeleteMapping("/{amenityTypeId}")
    public ResponseEntity<Void> delete(@PathVariable("amenityTypeId") Long amenityTypeId) {
        inRoomAmenityTypeService.delete(amenityTypeId);
        return ResponseEntity.noContent().build();
    }
}
