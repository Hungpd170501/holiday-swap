package com.example.holidayswap.controller.property.timeFrame;

import com.example.holidayswap.domain.dto.response.property.timeFrame.TimeFrameResponse;
import com.example.holidayswap.service.property.timeFame.TimeFrameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/time-frames")
public class TimeFramesController {
    private final TimeFrameService timeFrameService;

    @GetMapping("/co-owners")
    public ResponseEntity<Page<TimeFrameResponse>> getAllByPropertyId(
            @RequestParam Long propertyId,
            @RequestParam Long userId,
            @RequestParam String roomId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        var dtoResponses = timeFrameService.getAllByCoOwner(propertyId, userId, roomId, pageable);
        return ResponseEntity.ok(dtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeFrameResponse> get(
            @PathVariable("id") Long id) {
        var dtoResponse = timeFrameService.get(id);
        return ResponseEntity.ok(dtoResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        timeFrameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
