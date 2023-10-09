package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.entity.booking.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {

    List<BookingDetail> findAllByBookingId(long bookingId);
}
