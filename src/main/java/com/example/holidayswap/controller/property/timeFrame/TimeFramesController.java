package com.example.holidayswap.controller.property.timeFrame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.entity.property.timeFrame.TimeFrameStatus;
import com.example.holidayswap.service.property.timeFame.TimeFrameService;
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
@RequestMapping("api/v1/time-frames")
public class TimeFramesController {
    private final TimeFrameService timeFrameService;

    @GetMapping("/properties")
    public ResponseEntity<Page<TimeFrameResponse>> getAllByPropertyId(
            @RequestParam Long propertyId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponses = timeFrameService.getAllByPropertyId(propertyId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/resorts")
    public ResponseEntity<Page<TimeFrameResponse>> getAllByResortId(
            @RequestParam Long resortId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponses = timeFrameService.getAllByResortId(resortId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeFrameResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = timeFrameService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping
    public ResponseEntity<TimeFrameResponse> create(
            @RequestPart CoOwnerId ownershipId,
            @RequestPart TimeFrameRequest dtoRequest
    ) {
        var timeFrameResponse = timeFrameService.create(ownershipId, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeFrameResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(timeFrameResponse);
    }

    @PutMapping(value = "/{timeFrameId}")
    public ResponseEntity<Void> update(@PathVariable("timeFrameId") Long timeFrameId,
                                       @RequestBody TimeFrameStatus timeFrameStatus) {
        timeFrameService.update(timeFrameId, timeFrameStatus);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeFrameId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeFrameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
