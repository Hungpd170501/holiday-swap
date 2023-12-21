package com.example.holidayswap.controller.issuebooking;

import com.example.holidayswap.domain.dto.request.issue.IssueRequest;
import com.example.holidayswap.service.booking.IIssueBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/issues-booking")
public class IssueBookingController {
    private final IIssueBookingService issueBookingService;

    @PutMapping("/update-issue-booking")
    public void updateIssueBooking(@RequestBody IssueRequest request) throws InterruptedException {
        issueBookingService.updateIssueBooking(request.getIssueId(), request.getIssueDescription(), request.getIssueStatus());
    }
    @GetMapping("/get-all-issue-booking")
    public ResponseEntity<?> getAllIssueBooking(){
       return ResponseEntity.ok().body(issueBookingService.getAllIssueBooking());
    }
    @GetMapping("/get-issue-booking-by-id/{issueId}")
    public ResponseEntity<?> getIssueBookingById(@PathVariable("issueId") Long issueId){
        return ResponseEntity.ok().body(issueBookingService.getIssueBookingById(issueId));
    }
}
