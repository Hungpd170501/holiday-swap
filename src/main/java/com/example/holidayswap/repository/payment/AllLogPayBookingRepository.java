package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.AllLogPayBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface    AllLogPayBookingRepository extends JpaRepository<AllLogPayBooking, Long> {
    List<AllLogPayBooking> findAllByFromMemberId(long fromMemberId);
}
