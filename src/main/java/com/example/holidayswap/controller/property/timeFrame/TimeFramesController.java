package com.example.holidayswap.controller.property.timeFrame;

import com.example.holidayswap.domain.dto.request.property.timeFrame.TimeFrameRequest;
import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
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
        var vacationResponses = timeFrameService.getAllByPropertyId(propertyId, pageable);
        return ResponseEntity.ok(vacationResponses);
    }

    @GetMapping("/resorts")
    public ResponseEntity<Page<TimeFrameResponse>> getAllByResortId(
            @RequestParam Long resortId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var vacationResponses = timeFrameService.getAllByResortId(resortId, pageable);
        return ResponseEntity.ok(vacationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeFrameResponse> get(
            @PathVariable("id") Long id) {
        var vacationResponse = timeFrameService.get(id);
        return ResponseEntity.ok(vacationResponse);
    }

    @PostMapping
    public ResponseEntity<TimeFrameResponse> create(
            @RequestPart CoOwnerId ownershipId,
            @RequestPart TimeFrameRequest dtoRequest
    ) {
        var vacation = timeFrameService.create(ownershipId, dtoRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vacation.getId())
                .toUri();
        return ResponseEntity.created(location).body(vacation);
    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Void> update(@PathVariable Long id,
//                                       @RequestBody VacationUnitRequest dtoRequest) {
//        timeFrameService.update(id, dtoRequest);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(id)
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeFrameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
