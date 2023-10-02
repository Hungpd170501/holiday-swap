package com.example.holidayswap.scheduler;

import com.example.holidayswap.domain.entity.subscription.CronJobLog;
import com.example.holidayswap.domain.entity.subscription.CronJobStatus;
import com.example.holidayswap.domain.entity.subscription.PriceType;
import com.example.holidayswap.domain.entity.subscription.SubscriptionStatus;
import com.example.holidayswap.repository.subscription.CronJobLogRepository;
import com.example.holidayswap.repository.subscription.SubscriptionRepository;
import com.example.holidayswap.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionCronJob {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;
    private final CronJobLogRepository cronJobLogRepository;
    @Scheduled(cron = "0 0 12 * * ?")
    public void scheduleTaskWithCronExpression() {
        log.info("Cron Task :: Execution Time - {}", System.currentTimeMillis());
        subscriptionRepository.findAll().forEach(subscription -> {
            if (subscription.getCurrentPeriodEnd().toLocalDate().equals(LocalDate.now())
                    && subscription.getSubscriptionStatus().equals(SubscriptionStatus.ACTIVE)
                    && subscription.getPlan().getPriceType().equals(PriceType.RECURRING)) {
                var updatedSubscription =  subscriptionService.renewSubscriptions(subscription.getSubscriptionId());
                cronJobLogRepository.save(CronJobLog.builder().cronJobName(updatedSubscription.getSubscriptionId().toString()).cronJobStatus(CronJobStatus.SUCCESS).message("success").build());
            }
        });
    }
}
