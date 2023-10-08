package com.example.holidayswap.controller.property.timeFrame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.AvailableTimeRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.AvailableTimeResponse;
import com.example.holidayswap.service.property.timeFame.AvailableTimeService;
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
@RequestMapping("api/v1/available-time")
public class AvailableTimesController {
    private final AvailableTimeService availableTimeService;

    @GetMapping("/vacation-units")
    public ResponseEntity<Page<AvailableTimeResponse>> getAllByVacationUnitId(
            @RequestParam(value = "vacationUnitId") Long vacationUnitId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponses = availableTimeService.getAllByVacationUnitId(vacationUnitId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/properties")
    public ResponseEntity<Page<AvailableTimeResponse>> getAllByPropertyId(
            @RequestParam("vacationUnitId") Long vacationUnitId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponses = availableTimeService.getAllByPropertyId(vacationUnitId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/resort")
    public ResponseEntity<Page<AvailableTimeResponse>> getAllByResortId(
            @RequestParam Long vacationUnitId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponses = availableTimeService.getAllByResortId(vacationUnitId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailableTimeResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = availableTimeService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PostMapping("/{vacationUnitId}")
    public ResponseEntity<AvailableTimeResponse> create(
            @PathVariable("vacationUnitId") Long vacationUnitId,
            @RequestBody AvailableTimeRequest dtoRequest) {
        var dtoResponse = availableTimeService.create(vacationUnitId, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dtoResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(dtoResponse);
    }

    @PutMapping(value = "/{timeOffDepositId}")
    public ResponseEntity<Void> update(@PathVariable("timeOffDepositId") Long timeOffDepositId,
                                       @RequestBody AvailableTimeRequest dtoRequest) {
        availableTimeService.update(timeOffDepositId, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeOffDepositId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availableTimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
