package com.example.holidayswap.repository.payment;

import com.example.holidayswap.domain.entity.payment.AllLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllLogRepository extends JpaRepository<AllLog, Long> {
    List<AllLog> findAllByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
