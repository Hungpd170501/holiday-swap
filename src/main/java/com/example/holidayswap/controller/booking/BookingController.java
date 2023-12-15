package com.example.holidayswap.controller.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.response.booking.TimeHasBooked;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.service.FileService;
import com.example.holidayswap.service.booking.IBookingService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private final IBookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<EnumBookingStatus.BookingStatus> createBooking(@RequestBody BookingRequest bookingRequest) throws InterruptedException, IOException, WriterException {
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

    @GetMapping("/timeHasBooked")
    public ResponseEntity<List<TimeHasBooked>> getTimeHasBooked(@RequestParam Long timeFrameId, @RequestParam int year) {
        var listTimeHasBooked = bookingService.getTimeHasBooked(timeFrameId, year);
        return ResponseEntity.ok(listTimeHasBooked);
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) throws InterruptedException {
        var cancelBooking = bookingService.returnPointBooking(bookingId);
        return cancelBooking != null ? ResponseEntity.ok(cancelBooking) : ResponseEntity.badRequest().body("Can not cancel booking");
    }
    @GetMapping("/getqrcode/{uuid}")
    public ResponseEntity<?> generateQr(@PathVariable String uuid) throws IOException, WriterException {
        var history = bookingService.historyBookingByUUID(uuid);
        return history != null ? ResponseEntity.ok(history) : ResponseEntity.badRequest().body("Not Found");

    }
}
