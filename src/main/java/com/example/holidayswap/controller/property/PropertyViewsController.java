package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.vacation.PropertyViewRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyViewResponse;
import com.example.holidayswap.service.property.PropertyViewService;
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
@RequestMapping("api/v1/property-view")
public class PropertyViewsController {
    private final PropertyViewService propertyViewService;

    @GetMapping
    public ResponseEntity<Page<PropertyViewResponse>> gets(
            @RequestParam(defaultValue = "") String searchName,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponses = propertyViewService.gets(searchName, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyViewResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = propertyViewService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping
    public ResponseEntity<PropertyViewResponse> create(
            @RequestBody PropertyViewRequest dtoRequest) {
        var dtoResponse = propertyViewService.create(dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody PropertyViewRequest dtoRequest) {
        propertyViewService.update(id, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propertyViewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
