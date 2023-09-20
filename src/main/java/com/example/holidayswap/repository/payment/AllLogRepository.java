package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.AllLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllLogRepository extends JpaRepository<AllLog, Long> {
}
