package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.entity.booking.IssueBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueBookingRepository extends JpaRepository<IssueBooking, Long> {
    @Query(value = "SELECT * FROM public.issue_booking\n" +
            "ORDER BY issue_id ASC ", nativeQuery = true)
    List<IssueBooking> findAll(Long bookingId);
}
