package com.example.holidayswap.controller.property.vacation;

import com.example.holidayswap.domain.dto.request.property.vacation.TimeOffDepositRequest;
import com.example.holidayswap.domain.dto.response.property.vacation.TimeOffDepositResponse;
import com.example.holidayswap.service.property.vacation.TimeOffDepositService;
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
@RequestMapping("api/v1/timeOffDeposit")
public class TimeOffDepositsController {
    private final TimeOffDepositService timeOffDepositService;

    @GetMapping
    public ResponseEntity<Page<TimeOffDepositResponse>> gets(
            @RequestParam Long vacationId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var timeOffDepositResponses = timeOffDepositService.gets(vacationId, pageable);
        return ResponseEntity.ok(timeOffDepositResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeOffDepositResponse> get(
            @PathVariable("id") Long id) {
        var timeOffDepositResponse = timeOffDepositService.get(id);
        return ResponseEntity.ok(timeOffDepositResponse);
    }

    @GetMapping("/resort")
    public ResponseEntity<Page<TimeOffDepositResponse>> getByResort(
            @RequestParam Long resortId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var timeOffDepositResponse = timeOffDepositService.getByResortId(resortId, pageable);
        return ResponseEntity.ok(timeOffDepositResponse);
    }

    @PostMapping(value = "/{id}")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TimeOffDepositResponse> create(
            @PathVariable Long id,
            @RequestBody TimeOffDepositRequest timeOffDepositRequest) {
        var timeOffDeposit = timeOffDepositService.create(id, timeOffDepositRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeOffDeposit.getId())
                .toUri();
        return ResponseEntity.created(location).body(timeOffDeposit);
    }

    @PutMapping(value = "/{id}")//, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody TimeOffDepositRequest timeOffDepositRequest) {
        timeOffDepositService.update(id, timeOffDepositRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeOffDepositService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
