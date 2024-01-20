package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.booking.IssueBooking;
import com.example.holidayswap.repository.booking.IssueBookingRepository;
import com.example.holidayswap.service.payment.ITransferPointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueBookingServiceImpl implements IIssueBookingService {
    private final IssueBookingRepository issueBookingRepository;
    private final ITransferPointService transferPointService;

    @Override
    public void createIssueBooking(Long bookingId, String issue) {
        IssueBooking issueBooking = new IssueBooking();
        issueBooking.setBookingId(bookingId);
        issueBooking.setDescription(issue);
        issueBooking.setStatus(EnumBookingStatus.IssueBookingStatus.OPEN);
        issueBookingRepository.save(issueBooking);
    }

    @Override
    public void updateIssueBooking(Long issueId, String note, EnumBookingStatus.IssueBookingStatus status) throws InterruptedException {
        var issueBooking = issueBookingRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
        issueBooking.setNote(note);
        issueBooking.setStatus(status);
        if(status.name().equals(EnumBookingStatus.IssueBookingStatus.REFUND.name())){
            transferPointService.refundPointBookingToUser(issueBooking.getBookingId());
        }
        issueBookingRepository.save(issueBooking);
    }

    @Override
    public List<IssueBooking> getAllIssueBooking() {
        return issueBookingRepository.findAll();
    }

    @Override
    public IssueBooking getIssueBookingById(Long issueId) {
        return issueBookingRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    @Override
    public IssueBooking getIssueBookingByBookingId(Long bookingId) {
        return issueBookingRepository.findByBookingId(bookingId);
    }
}
