package com.example.holidayswap.controller.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import com.example.holidayswap.service.property.amenity.InRoomAmenityService;
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
@RequestMapping("api/v1/in-room-amenities")
public class InRoomAmenitiesController {
    final private InRoomAmenityService inRoomAmenityService;

    @GetMapping
    public ResponseEntity<Page<InRoomAmenityResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(inRoomAmenityService.gets(name, pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<InRoomAmenityResponse> get(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(inRoomAmenityService.get(id));
    }

    @PostMapping
    public ResponseEntity<InRoomAmenityResponse> create(
            @RequestBody InRoomAmenityRequest dtoRequest) {
        var dtoResponse = inRoomAmenityService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody InRoomAmenityRequest dtoRequest) {
        inRoomAmenityService.update(id, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inRoomAmenityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
