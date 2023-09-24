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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/properties")
public class PropertiesController {
    private final PropertyService propertyService;

    @GetMapping("/search")
    public ResponseEntity<Page<PropertyResponse>> gets(@RequestParam(defaultValue = "") Long resortId, @RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var properties = propertyService.gets(pageable);
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> get(@PathVariable("id") Long id) {
        var property = propertyService.get(id);
        return ResponseEntity.ok(property);
    }

    @PostMapping
//            (consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE},
//            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyResponse> create(
            @RequestPart List<MultipartFile> propertyImages,
            @RequestPart List<MultipartFile> propertyContractImages,
            @RequestPart Long userId,
            @RequestPart PropertyRegisterRequest propertyRegisterRequest
    ) throws IOException {
        var propertyCreated = propertyService.create(userId, propertyRegisterRequest, propertyImages, propertyContractImages);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(propertyService.get(propertyCreated.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PropertyUpdateRequest propertyUpdateRequest) {
        propertyService.update(id, propertyUpdateRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propertyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
