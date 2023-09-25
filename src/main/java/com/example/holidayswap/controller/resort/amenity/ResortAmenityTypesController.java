package com.example.holidayswap.controller.resort.amenity;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityTypeResponse;
import com.example.holidayswap.service.resort.amenity.ResortAmenityTypeService;
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
@RequestMapping("api/v1/resortAmenityTypes")
public class ResortAmenityTypesController {
    private final ResortAmenityTypeService resortAmenityTypeService;

    @GetMapping("/search")
    public ResponseEntity<Page<ResortAmenityTypeResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(resortAmenityTypeService.gets(name, pageable));
    }

    @GetMapping("/amenityType/resort")
    public ResponseEntity<List<ResortAmenityTypeResponse>> gets(
            @RequestParam Long resortId) {
        return ResponseEntity.ok(resortAmenityTypeService.gets(resortId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResortAmenityTypeResponse> get(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(resortAmenityTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<ResortAmenityTypeResponse> create(
            @RequestBody ResortAmenityTypeRequest dtoRequest) {
        var dtoResponse = resortAmenityTypeService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody ResortAmenityTypeRequest dtoRequest) {
        resortAmenityTypeService.update(id, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resortAmenityTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
