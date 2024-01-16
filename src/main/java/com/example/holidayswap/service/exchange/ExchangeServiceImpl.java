package com.example.holidayswap.service.exchange;

import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeUpdatingRequest;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.exchange.Exchange;
import com.example.holidayswap.domain.entity.exchange.ExchangeStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.exchange.ExchangeMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.exchange.ExchangeRepository;
import com.example.holidayswap.service.booking.IBookingService;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.utils.AuthUtils;
import com.example.holidayswap.utils.RedissonLockUtils;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class ExchangeServiceImpl implements IExchangeService {
    private final ITransferPointService transferPointService;
    private final ExchangeRepository exchangeRepository;
    private final IBookingService bookingService;
    private final BookingRepository bookingRepository;
    private final AuthUtils authUtils;
    private final SimpMessagingTemplate messagingTemplate;
    private final PushNotificationService pushNotificationService;

    @Override
    public ExchangeResponse create(ExchangeCreatingRequest exchangeCreatingRequest) {
        var exchange = ExchangeMapper.INSTANCE.toExchangeEntity(exchangeCreatingRequest);
        var user = authUtils.getAuthenticatedUser();
        exchange.setRequestUserId(user.getUserId());
        exchange.setRequestStatus(ExchangeStatus.CONVERSATION);
        exchange.setStatus(ExchangeStatus.CONVERSATION);
        exchange.setOverallStatus(ExchangeStatus.CONVERSATION);
        var savedExchange = exchangeRepository.save(exchange);
        CompletableFuture.runAsync(() -> pushNotificationService.createNotification(
                NotificationRequest.builder()
                        .subject("New exchange.")
                        .toUserId(exchange.getUserId())
                        .content("Received new exchange request from " + user.getUserId() + ".").build()
        ));
        return ExchangeMapper.INSTANCE.toExchangeResponse(savedExchange);
    }

    @Override
    public void updateBaseData(Long exchangeId, ExchangeUpdatingRequest exchangeUpdatingRequest) {
        var exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new EntityNotFoundException("Exchange not found"));
        var user = authUtils.getAuthenticatedUser();
        if (exchange.getOverallStatus().equals(ExchangeStatus.CONVERSATION)) {
            if (exchange.getUserId().equals(user.getUserId())) {
                exchange.setCheckInDate(exchangeUpdatingRequest.getCheckInDate());
                exchange.setCheckOutDate(exchangeUpdatingRequest.getCheckOutDate());
                exchange.setTotalMember(exchangeUpdatingRequest.getTotalMember());
            }
            if (exchange.getRequestUserId().equals(user.getUserId())) {
                exchange.setRequestCheckInDate(exchangeUpdatingRequest.getCheckInDate());
                exchange.setRequestCheckOutDate(exchangeUpdatingRequest.getCheckOutDate());
                exchange.setRequestTotalMember(exchangeUpdatingRequest.getTotalMember());
            }
            exchange.setRequestStatus(ExchangeStatus.CONVERSATION);
            exchange.setStatus(ExchangeStatus.CONVERSATION);
            exchange.setOverallStatus(ExchangeStatus.CONVERSATION);
            exchangeRepository.save(exchange);
            String destination = "/topic/exchange-" + exchangeId;
            messagingTemplate.convertAndSend(destination,
                    ExchangeMapper.INSTANCE.toExchangeResponse(exchange));
            messagingTemplate.convertAndSend("/topic/exchangeStep-" + exchangeId,
                    "0");
        } else {
            throw new IllegalArgumentException("Must be in conversation round!");
        }
    }

    @Override
    @Transactional
    public void updateNextStatus(Long exchangeId) throws InterruptedException {
        var exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new EntityNotFoundException("Exchange not found"));
        var user = authUtils.getAuthenticatedUser();
        RLock fairLock = RedissonLockUtils.getFairLock("exchange-" + exchange.getExchangeId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                if (exchange.getUserId().equals(user.getUserId()) || exchange.getRequestUserId().equals(user.getUserId())) {
                    ExchangeStatus currentStatus = exchange.getOverallStatus();
                    switch (currentStatus) {
                        case CONVERSATION -> handleStatusUpdate(exchange, user, ExchangeStatus.PRE_CONFIRMATION, "1");
                        case PRE_CONFIRMATION -> handleStatusUpdate(exchange, user, ExchangeStatus.CONFIRMATION, "2");
                        case CONFIRMATION -> handleStatusUpdate(exchange, user, ExchangeStatus.SUCCESS, "3");
                        default -> throw new IllegalArgumentException("Invalid status!");
                    }
                }
            } finally {
                fairLock.unlock();
            }
        }
    }

    private void handleStatusUpdate(Exchange exchange, User user, ExchangeStatus nextStatus, String step) {
        if (exchange.getUserId().equals(user.getUserId())) {
            exchange.setStatus(nextStatus);
        } else {
            exchange.setRequestStatus(nextStatus);
        }
        if (exchange.getStatus().equals(exchange.getRequestStatus())) {
            exchange.setOverallStatus(nextStatus);
            messagingTemplate.convertAndSend("/topic/exchangeStep-" + exchange.getExchangeId(), step);
        }
        exchangeRepository.save(exchange);
    }

    @Override
    public void updatePreviousStatus(Long exchangeId) throws InterruptedException {
        var exchange = exchangeRepository.findById(exchangeId).orElseThrow(()
                -> new EntityNotFoundException("Exchange not found"));
        var user = authUtils.getAuthenticatedUser();
        if (exchange.getOverallStatus().equals(ExchangeStatus.PRE_CONFIRMATION)
                && (exchange.getUserId().equals(user.getUserId()) || exchange.getRequestUserId().equals(user.getUserId()))) {
            RLock fairLock = RedissonLockUtils.getFairLock("exchange-" + exchange.getExchangeId());
            boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
            if (tryLock) {
                try {
                    exchange.setStatus(ExchangeStatus.CONVERSATION);
                    exchange.setRequestStatus(ExchangeStatus.CONVERSATION);
                    exchange.setOverallStatus(ExchangeStatus.CONVERSATION);
                    exchangeRepository.save(exchange);
                    messagingTemplate.convertAndSend("/topic/exchangeStep-" + exchangeId,
                            "0");
                } finally {
                    fairLock.unlock();
                }
            }

        }
    }

    @Override
    public void cancel(Long exchangeId) throws InterruptedException {
        var exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new EntityNotFoundException("Exchange not found"));
        var user = authUtils.getAuthenticatedUser();
        if (!exchange.getOverallStatus().equals(ExchangeStatus.SUCCESS)
                && (exchange.getUserId().equals(user.getUserId()) || exchange.getRequestUserId().equals(user.getUserId()))) {
            RLock fairLock = RedissonLockUtils.getFairLock("exchange-" + exchange.getExchangeId());
            boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
            if (tryLock) {
                try {
                    if (exchange.getUserId().equals(user.getUserId())) {
                        exchange.setStatus(ExchangeStatus.CANCEL);
                    } else {
                        exchange.setRequestStatus(ExchangeStatus.CANCEL);
                    }
                    exchange.setOverallStatus(ExchangeStatus.CANCEL);
                    exchangeRepository.save(exchange);
                    messagingTemplate.convertAndSend("/topic/exchangeStep-" + exchangeId,
                            "-1");
                } finally {
                    fairLock.unlock();
                }
            }
        }
    }
}
