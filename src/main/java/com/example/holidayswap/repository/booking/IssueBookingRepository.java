package com.example.holidayswap.repository.booking;

import com.example.holidayswap.domain.entity.booking.IssueBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueBookingRepository extends JpaRepository<IssueBooking, Long> {

}
