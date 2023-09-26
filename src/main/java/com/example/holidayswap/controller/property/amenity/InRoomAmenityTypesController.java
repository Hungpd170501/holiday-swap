package com.example.holidayswap.controller.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityTypeResponse;
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
@RequestMapping("api/v1/inRoomAmenityTypes")
public class InRoomAmenityTypesController {
    final private InRoomAmenityTypeService inRoomAmenityTypeService;

    @GetMapping("/search")
    public ResponseEntity<Page<InRoomAmenityTypeResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(inRoomAmenityTypeService.gets(name, pageable));
    }

    @GetMapping("/amenityType/property")
    public ResponseEntity<List<InRoomAmenityTypeResponse>> gets(
            @RequestParam Long propertyId) {
        return ResponseEntity.ok(inRoomAmenityTypeService.gets(propertyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InRoomAmenityTypeResponse> get(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(inRoomAmenityTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<InRoomAmenityTypeResponse> create(
            @RequestBody InRoomAmenityTypeRequest dtoRequest) {
        var dtoResponse = inRoomAmenityTypeService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody InRoomAmenityTypeRequest dtoRequest) {
        inRoomAmenityTypeService.update(id, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inRoomAmenityTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
