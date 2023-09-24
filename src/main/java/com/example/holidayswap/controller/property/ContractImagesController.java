package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.service.property.ContractImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/contractImages")
public class ContractImagesController {
    private final ContractImageService contractImageService;

    @GetMapping
    public ResponseEntity<List<ContractImageResponse>> gets(
            @RequestParam Long contractId) {
        var inRoomAmenityResponses = contractImageService.gets(contractId);
        return ResponseEntity.ok(inRoomAmenityResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractImageResponse> get(
            @PathVariable("id") Long id) {
        var inRoomAmenityResponse = contractImageService.get(id);
        return ResponseEntity.ok(inRoomAmenityResponse);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContractImageResponse> create(
            @PathVariable Long id,
            @RequestPart MultipartFile contractImage) {
        var contractImageCreated = contractImageService.create(id, contractImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(contractImageCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(contractImageCreated);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart MultipartFile contractImage) {
        contractImageService.update(id, contractImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contractImageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
