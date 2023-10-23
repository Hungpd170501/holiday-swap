package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.dto.request.booking.UserOfBookingRequest;
import com.example.holidayswap.domain.entity.booking.UserOfBooking;
import com.example.holidayswap.repository.booking.UserOfBookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class UserOfBookingServiceImpl implements IUserOfBookingService{

    private final UserOfBookingRepository userOfBookingRepository;

    @Override
    public void saveUserOfBooking(Long bookingId, List<UserOfBookingRequest> userOfBookingRequests) {
        for (UserOfBookingRequest userOfBookingRequest : userOfBookingRequests) {
            UserOfBooking userOfBooking = new UserOfBooking();
            userOfBooking.setBookingId(bookingId);
            userOfBooking.setFullName(userOfBookingRequest.getFullName());
            userOfBooking.setEmail(userOfBookingRequest.getEmail());
            userOfBooking.setPhoneNumber(userOfBookingRequest.getPhoneNumber());
            userOfBookingRepository.save(userOfBooking);
        }
    }
}
