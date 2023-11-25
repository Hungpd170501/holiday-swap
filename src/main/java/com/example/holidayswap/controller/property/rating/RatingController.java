package com.example.holidayswap.controller.property.rating;

import com.example.holidayswap.domain.dto.request.property.rating.RatingRequest;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
import com.example.holidayswap.service.property.rating.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<RatingResponse>> gets(
            @RequestParam Long propertyId,
            @RequestParam String roomId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        var inRoomAmenityTypeResponses = ratingService.getListRatingByPropertyId(propertyId, roomId, pageable);
        return ResponseEntity.ok(inRoomAmenityTypeResponses);
    }

    @GetMapping("/property/{bookingId}")
    public ResponseEntity<RatingResponse> get(@PathVariable("bookingId") Long bookingId) {
        var e = ratingService.getRatingByBookingId(bookingId);
        return ResponseEntity.ok(e);
    }

    @PostMapping("/property/{bookingId}/user/{userId}")
    public ResponseEntity<Void> create(@PathVariable("bookingId") Long bookingId, @PathVariable("userId") Long userId, @RequestBody RatingRequest dto) {
        ratingService.create(bookingId, userId, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/property/{bookingId}/user/{userId}")
    public ResponseEntity<Void> update(@PathVariable("bookingId") Long bookingId, @PathVariable("userId") Long userId, @RequestBody RatingRequest dto) {
        ratingService.update(bookingId, userId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/property/{bookingId}/user/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("bookingId") Long bookingId, @PathVariable("userId") Long userId) {
        ratingService.deleteRatingById(bookingId, userId);
        return ResponseEntity.noContent().build();
    }
}
