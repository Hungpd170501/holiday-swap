package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.entity.booking.UserOfBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOfBookingRepository extends JpaRepository<UserOfBooking, Long> {

    List<UserOfBooking> findAllByBookingId(Long bookingId);

}
