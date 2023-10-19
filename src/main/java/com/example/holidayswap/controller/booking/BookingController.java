package com.example.holidayswap.controller.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.service.booking.IBookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private final IBookingService bookingService;
    @PostMapping("/create")
    public ResponseEntity<EnumBookingStatus.BookingStatus> createBooking(@RequestBody BookingRequest bookingRequest) throws InterruptedException {
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }
    @GetMapping("/historybooking")
    public ResponseEntity<?> getHistoryBooking(){
        var historyBooking = bookingService.historyBookingUserLogin();
        return historyBooking != null ? ResponseEntity.ok(historyBooking) : ResponseEntity.badRequest().body("Empty");
    }
}
