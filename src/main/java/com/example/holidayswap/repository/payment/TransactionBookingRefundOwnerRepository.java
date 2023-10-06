package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.TransactionBookingRefundOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBookingRefundOwnerRepository extends JpaRepository<TransactionBookingRefundOwner, Long> {
}
