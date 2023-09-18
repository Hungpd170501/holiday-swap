package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.TransactLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactLogRepository extends JpaRepository<TransactLog, Long> {
}
