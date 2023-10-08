package com.example.holidayswap.repository.subscription;

import com.example.holidayswap.domain.entity.subscription.CronJobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronJobLogRepository extends JpaRepository<CronJobLog, Long> {
}
