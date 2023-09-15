package com.example.holidayswap.controller.property.inRoomAmenity;

import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityTypeResponse;
import com.example.holidayswap.service.property.inRoomAmenityTypeService.InRoomAmenityTypeService;
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
        var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(name, pageable);
        return ResponseEntity.ok(inRoomAmenityTypeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InRoomAmenityTypeResponse> get(
            @PathVariable("id") Long id) {
        var inRoomAmenityTypeResponse = inRoomAmenityTypeService.get(id);
        return ResponseEntity.ok(inRoomAmenityTypeResponse);
    }

    @PostMapping
    public ResponseEntity<InRoomAmenityTypeResponse> create(
            @RequestBody InRoomAmenityTypeRequest inRoomAmenityTypeRequest) {
        var inRoomAmenityTypeCreated = inRoomAmenityTypeService.create(inRoomAmenityTypeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inRoomAmenityTypeCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(inRoomAmenityTypeCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody InRoomAmenityTypeRequest inRoomAmenityTypeRequest) {
        inRoomAmenityTypeService.update(id, inRoomAmenityTypeRequest);
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
