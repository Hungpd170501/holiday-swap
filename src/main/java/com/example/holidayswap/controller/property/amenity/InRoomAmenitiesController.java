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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/in-room-amenities")
public class InRoomAmenitiesController {
    final private InRoomAmenityService inRoomAmenityService;

    @GetMapping
    public ResponseEntity<Page<InRoomAmenityResponse>> gets(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(inRoomAmenityService.gets(name, pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<InRoomAmenityResponse>> getList() {
        return ResponseEntity.ok(inRoomAmenityService.gets());
    }

    @GetMapping("/{inRoomAmenityId}")
    public ResponseEntity<InRoomAmenityResponse> get(@PathVariable("inRoomAmenityId") Long inRoomAmenityId) {
        return ResponseEntity.ok(inRoomAmenityService.get(inRoomAmenityId));
    }

    @PostMapping
    public ResponseEntity<InRoomAmenityResponse> create(@RequestPart(name = "inRoomAmenity") InRoomAmenityRequest dtoRequest, @RequestPart(name = "inRoomAmenityIcon") MultipartFile inRoomAmenityIcon) {
        var dtoResponse = inRoomAmenityService.create(dtoRequest, inRoomAmenityIcon);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getId()).toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{inRoomAmenityId}")
    public ResponseEntity<InRoomAmenityResponse> update(@PathVariable("inRoomAmenityId") Long inRoomAmenityId, @RequestPart(name = "inRoomAmenity") InRoomAmenityRequest inRoomAmentity, @RequestPart(name = "inRoomAmenityIcon", required = false) MultipartFile inRoomAmenityIcon) {
        var dtoResponse = inRoomAmenityService.update(inRoomAmenityId, inRoomAmentity, inRoomAmenityIcon);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(inRoomAmenityId).toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @DeleteMapping("/{inRoomAmenityId}")
    public ResponseEntity<Void> delete(@PathVariable("inRoomAmenityId") Long inRoomAmenityId) {
        inRoomAmenityService.delete(inRoomAmenityId);
        return ResponseEntity.noContent().build();
    }
}
