package com.example.holidayswap.controller.resort;

import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import com.example.holidayswap.service.resort.ResortImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/resort-images")
public class ResortImagesController {
    final private ResortImageService resortImageService;

    @GetMapping("/{resortImageId}")
    public ResponseEntity<ResortImageResponse> get(
            @PathVariable("resortImageId") Long resortImageId) {
        var dtoResponse = resortImageService.get(resortImageId);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping
    public ResponseEntity<ResortImageResponse> create(
            @RequestPart("resortId") Long resortId,
            @RequestPart MultipartFile resortImage) {
        var dtoResponse = resortImageService.create(resortId, resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{resortImageId}")
    public ResponseEntity<ResortImageResponse> update(@PathVariable("resortImageId") Long resortImageId,
                                       @RequestPart MultipartFile resortImage) {
        var dtoReponse = resortImageService.update(resortImageId, resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortImageId)
                .toUri();
        return ResponseEntity.created(location).body(dtoReponse);
    }

    @DeleteMapping("/{resortImageId}")
    public ResponseEntity<Void> delete(@PathVariable("resortImageId") Long resortImageId) {
        resortImageService.delete(resortImageId);
        return ResponseEntity.noContent().build();
    }
}
