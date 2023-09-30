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
@RequestMapping("api/v1/resortImages")
public class ResortImagesController {
    final private ResortImageService resortImageService;

    @GetMapping("/{id}")
    public ResponseEntity<ResortImageResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = resortImageService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping
    public ResponseEntity<ResortImageResponse> create(
            @RequestPart Long resortId,
            @RequestPart MultipartFile resortImage) {
        var dtoResponse = resortImageService.create(resortId, resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestPart MultipartFile resortImage) {
        resortImageService.update(id, resortImage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resortImageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
