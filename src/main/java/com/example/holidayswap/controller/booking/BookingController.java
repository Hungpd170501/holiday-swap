package com.example.holidayswap.controller.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.service.booking.IBookingService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private final IBookingService bookingService;
    @PostMapping("/create")
    public ResponseEntity<EnumBookingStatus.BookingStatus> createBooking(@RequestBody BookingRequest bookingRequest) throws InterruptedException, FirebaseMessagingException {
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }
    @GetMapping("/historybooking")
    public ResponseEntity<?> getHistoryBooking(){
        var historyBooking = bookingService.historyBookingUserLogin();
        return historyBooking != null ? ResponseEntity.ok(historyBooking) : ResponseEntity.badRequest().body("Empty");
    }

    @GetMapping("/historybooking/{bookingId}")
    public ResponseEntity<?> getHistoryBookingDetail(@PathVariable Long bookingId){
        var historyBookingDetail = bookingService.historyBookingDetail(bookingId);
        return historyBookingDetail != null ? ResponseEntity.ok(historyBookingDetail) : ResponseEntity.badRequest().body("Not Found");
    }

    @GetMapping("/ownerhistorybooking")
    public ResponseEntity<?> getOwnerHistoryBooking(){
        var historyBooking = bookingService.historyBookingOwnerLogin();
        return historyBooking != null ? ResponseEntity.ok(historyBooking) : ResponseEntity.badRequest().body("Empty");
    }

    @GetMapping("/ownerhistorybooking/{bookingId}")
    public ResponseEntity<?> getOwnerHistoryBookingDetail(@PathVariable Long bookingId){
        var historyBookingDetail = bookingService.historyBookingDetailOwner(bookingId);
        return historyBookingDetail != null ? ResponseEntity.ok(historyBookingDetail) : ResponseEntity.badRequest().body("Not Found");
    }
}
