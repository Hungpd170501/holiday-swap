package com.example.holidayswap.controller.property.timeFrame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTimeStatus;
import com.example.holidayswap.service.property.timeFame.AvailableTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/available-times")
public class AvailableTimesController {
    private final AvailableTimeService availableTimeService;

    @GetMapping("/co-owner/{CoOwnerId}")
    public ResponseEntity<Page<AvailableTimeResponse>> getAllByCoOwnerId(
            @PathVariable(value = "CoOwnerId") Long CoOwnerId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        var dtoResponses = availableTimeService.getAllByCoOwnerId(CoOwnerId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/properties")
    public ResponseEntity<Page<AvailableTimeResponse>> getAllByPropertyId(
            @RequestParam("timeFrameId") Long timeFrameId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        var dtoResponses = availableTimeService.getAllByPropertyId(timeFrameId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/getAvailableTimeCreated")
    public ResponseEntity<List<AvailableTimeResponse>> getAvailableTimeCreated(
            @RequestParam("coOwnerId") Long coOwnerId,
            @RequestParam int year) {
        var dtoResponses = availableTimeService.getAllByCoOwnerIdAndYear(coOwnerId, year);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/getAllByCoOwnerId")
    public ResponseEntity<List<AvailableTimeResponse>> getAllByCoOwnerIdAndBetweenTimeAndTime(
            @RequestParam("coOwnerId") Long coOwnerId
    ) {
        var dtoResponses = availableTimeService.getAllByCoOwnerId(coOwnerId);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/user/resort/properties")
    public ResponseEntity<Page<AvailableTimeResponse>> getAllViaPropertyId(
            @RequestParam("timeFrameId") Long timeFrameId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        var dtoResponses = availableTimeService.getAllByPropertyId(timeFrameId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailableTimeResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = availableTimeService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping("/{timeFrameId}")
    public ResponseEntity<AvailableTimeResponse> create(
            @PathVariable("timeFrameId") Long timeFrameId,
            @RequestBody AvailableTimeRequest dtoRequest) {
        availableTimeService.create(timeFrameId, dtoRequest);

        return ResponseEntity.created(null).build();
    }

    @PutMapping(value = "/{availableTimeId}")
    public ResponseEntity<Void> update(@PathVariable("availableTimeId") Long timeFrameId,
                                       @RequestBody AvailableTimeRequest dtoRequest) {
        availableTimeService.update(timeFrameId, dtoRequest);
        return ResponseEntity.created(null).build();
    }

    @PutMapping(value = "/{availableTimeId}/status")
    public ResponseEntity<Void> update(@PathVariable("availableTimeId") Long availableTimeId,
                                       @RequestBody AvailableTimeStatus availableTimeStatus) {
        availableTimeService.update(availableTimeId, availableTimeStatus);

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availableTimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
