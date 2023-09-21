package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.request.property.PropertyContractRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyContractResponse;
import com.example.holidayswap.service.property.PropertyContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/propertyContracts")
public class PropertyContractsController {
    private final PropertyContractService propertyContractService;

    @GetMapping
    public ResponseEntity<List<PropertyContractResponse>> gets(
            @RequestParam Long propertyId) {
        var propertyContractResponses = propertyContractService.gets(propertyId);
        return ResponseEntity.ok(propertyContractResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyContractResponse> get(
            @PathVariable("id") Long id) {
        var propertyContractResponse = propertyContractService.get(id);
        return ResponseEntity.ok(propertyContractResponse);
    }

    @PostMapping(value = "/{id}")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PropertyContractResponse> create(
            @PathVariable Long id,
            @RequestBody PropertyContractRequest propertyContractRequest) {
        var propertyContract = propertyContractService.create(id, propertyContractRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propertyContract.getId())
                .toUri();
        return ResponseEntity.created(location).body(propertyContract);
    }

    @PutMapping(value = "/{id}")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody PropertyContractRequest propertyContractRequest) {
        propertyContractService.update(id, propertyContractRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        propertyContractService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
