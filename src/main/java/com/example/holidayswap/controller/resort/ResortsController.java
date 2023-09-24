package com.example.holidayswap.controller.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortResponse;
import com.example.holidayswap.service.resort.ResortService;
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
@RequestMapping("api/v1/resorts")
public class ResortsController {
    final private ResortService resortService;

    @GetMapping("/search")
    public ResponseEntity<Page<ResortResponse>> gets(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var resortResponses = resortService.gets(pageable);
        return ResponseEntity.ok(resortResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResortResponse> get(
            @PathVariable("id") Long id) {
        var inRoomAmenityTypeResponse = resortService.get(id);
        return ResponseEntity.ok(inRoomAmenityTypeResponse);
    }

    @PostMapping
    public ResponseEntity<ResortResponse> create(
            @RequestBody ResortRequest resortRequest) {
        var resortRequestCreated = resortService.create(resortRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resortRequestCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(resortRequestCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody ResortRequest resortRequest) {
        resortService.update(id, resortRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resortService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
