package com.example.holidayswap.service.exchange;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeUpdatingRequest;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeWithDetailResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.exchange.Exchange;
import com.example.holidayswap.domain.entity.exchange.ExchangeStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.exchange.ExchangeMapper;
import com.example.holidayswap.domain.mapper.property.ApartmentForRentMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.exchange.ExchangeRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.service.EmailService;
import com.example.holidayswap.service.auth.UserService;
import com.example.holidayswap.service.booking.IBookingService;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.service.property.ApartmentForRentService;
import com.example.holidayswap.utils.AuthUtils;
import com.example.holidayswap.utils.RedissonLockUtils;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class ExchangeServiceImpl implements IExchangeService {
    private final ITransferPointService transferPointService;
    private final ExchangeRepository exchangeRepository;
    private final IBookingService bookingService;
    private final BookingRepository bookingRepository;
    private final AuthUtils authUtils;
    private final SimpMessagingTemplate messagingTemplate;
    private final PushNotificationService pushNotificationService;
    private final ApartmentForRentService apartmentForRentService;
    private final AvailableTimeRepository availableTimeRepository;
    private final UserService userService;
    private final EmailService emailService;

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
        var exchangeUpdatingResponse = ExchangeMapper.INSTANCE.toExchangeUpdatingResponse(exchangeUpdatingRequest);
        var user = authUtils.getAuthenticatedUser();
        if (exchange.getOverallStatus().equals(ExchangeStatus.CONVERSATION)) {
            if (exchange.getUserId().equals(user.getUserId())) {
                exchange.setCheckInDate(exchangeUpdatingRequest.getCheckInDate());
                exchange.setCheckOutDate(exchangeUpdatingRequest.getCheckOutDate());
                exchange.setTotalMember(exchangeUpdatingRequest.getTotalMember());
                exchangeUpdatingResponse.setAvailableTimeId(exchange.getAvailableTimeId());
                exchangeUpdatingResponse.setUserId(exchange.getUserId());
                messagingTemplate.convertAndSend("/topic/exchange-" + exchangeId + "-" + exchange.getRequestUserId(),
                        exchangeUpdatingResponse);
            }
            if (exchange.getRequestUserId().equals(user.getUserId())) {
                exchange.setRequestCheckInDate(exchangeUpdatingRequest.getCheckInDate());
                exchange.setRequestCheckOutDate(exchangeUpdatingRequest.getCheckOutDate());
                exchange.setRequestTotalMember(exchangeUpdatingRequest.getTotalMember());
                exchangeUpdatingResponse.setAvailableTimeId(exchange.getRequestAvailableTimeId());
                exchangeUpdatingResponse.setUserId(exchange.getRequestUserId());
                messagingTemplate.convertAndSend("/topic/exchange-" + exchangeId + "-" + exchange.getUserId(),
                        exchangeUpdatingResponse);
            }
            bookingRepository.findById(exchange.getBookingId()).ifPresent(booking -> {
                bookingRepository.deleteById(booking.getId());
            });
            bookingRepository.findById(exchange.getRequestBookingId()).ifPresent(booking -> {
                bookingRepository.deleteById(booking.getId());
            });
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
    public void updateNextStatus(Long exchangeId) throws InterruptedException, MessagingException, IOException, WriterException {
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

    private void handleStatusUpdate(Exchange exchange, User user, ExchangeStatus nextStatus, String step) throws MessagingException, IOException, InterruptedException, WriterException {
        if (exchange.getUserId().equals(user.getUserId())) {
            exchange.setStatus(nextStatus);
            handleBookingUpdate(exchange, user, nextStatus, exchange.getAvailableTimeId(), exchange.getCheckInDate(), exchange.getCheckOutDate(), exchange.getTotalMember());
        } else {
            exchange.setRequestStatus(nextStatus);
            handleBookingUpdate(exchange, user, nextStatus, exchange.getRequestAvailableTimeId(), exchange.getRequestCheckInDate(), exchange.getRequestCheckOutDate(), exchange.getRequestTotalMember());
        }

        if (exchange.getStatus().equals(exchange.getRequestStatus())) {
            exchange.setOverallStatus(nextStatus);
            messagingTemplate.convertAndSend("/topic/exchangeStep-" + exchange.getExchangeId(), step);
            if (exchange.getOverallStatus().equals(ExchangeStatus.SUCCESS)) {
                bookingRepository.findById(exchange.getBookingId()).ifPresent(booking -> {
                    booking.setStatus(EnumBookingStatus.BookingStatus.SUCCESS);
                    bookingRepository.save(booking);
                });
                bookingRepository.findById(exchange.getRequestBookingId()).ifPresent(booking -> {
                    booking.setStatus(EnumBookingStatus.BookingStatus.SUCCESS);
                    bookingRepository.save(booking);
                });
                CompletableFuture.runAsync(() -> {
                    var recipient = userService.getUserById(exchange.getUserId());
                    bookingRepository.findById(exchange.getBookingId()).ifPresent(booking -> {
                        pushNotificationService.createNotification(
                                NotificationRequest.builder()
                                        .subject("Exchange success.")
                                        .toUserId(exchange.getUserId())
                                        .content("Your exchange with "+ recipient.getUsername() + "has been success.").build());
                        try {
                            emailService.sendConfirmBookedHtml(booking, recipient.getEmail());
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }

                    });
                });
                CompletableFuture.runAsync(() -> {
                    var recipient = userService.getUserById(exchange.getRequestUserId());
                    bookingRepository.findById(exchange.getRequestBookingId()).ifPresent(booking -> {
                        pushNotificationService.createNotification(
                                NotificationRequest.builder()
                                        .subject("Exchange success.")
                                        .toUserId(exchange.getUserId())
                                        .content("Your exchange with "+ recipient.getUsername() + "has been success.").build());
                        try {
                            emailService.sendConfirmBookedHtml(booking, recipient.getEmail());
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }

                    });
                });
            }

        }

        exchangeRepository.save(exchange);
    }

    private void handleBookingUpdate(Exchange exchange, User user, ExchangeStatus nextStatus, Long availableTimeId, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuest) throws MessagingException, IOException, InterruptedException, WriterException {
        if (nextStatus.equals(ExchangeStatus.PRE_CONFIRMATION)) {
            Long bookingId = bookingService.createBookingExchange(
                    BookingRequest.builder()
                            .userId(user.getUserId())
                            .availableTimeId(availableTimeId)
                            .checkInDate(Date.from(checkInDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                            .checkOutDate(Date.from(checkOutDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                            .numberOfGuest(numberOfGuest)
                            .build()
            );

            if (user.getUserId().equals(exchange.getRequestUserId())) {
                exchange.setRequestBookingId(bookingId);
            } else {
                exchange.setBookingId(bookingId);
            }
            exchangeRepository.save(exchange);
        }
        if (nextStatus.equals(ExchangeStatus.SUCCESS)) {
            bookingService.payBookingExchange(exchange.getBookingId());
        }
    }

    @Transactional
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
                    bookingRepository.findById(exchange.getBookingId()).ifPresent(booking -> {
                        bookingRepository.deleteById(booking.getId());
                    });
                    bookingRepository.findById(exchange.getRequestBookingId()).ifPresent(booking -> {
                        bookingRepository.deleteById(booking.getId());
                    });
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
                    if (exchange.getBookingId() != null) {
                        bookingService.cancelBookingExchange(exchange.getBookingId());
                    }
                    if (exchange.getRequestBookingId() != null) {
                        bookingService.cancelBookingExchange(exchange.getRequestBookingId());
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

    @Override
    public ExchangeResponse getExchangeById(Long exchangeId) {
        return exchangeRepository.findById(exchangeId)
                .map(ExchangeMapper.INSTANCE::toExchangeResponse)
                .orElseThrow(() -> new EntityNotFoundException("Exchange not found."));
    }

    @Override
    public Page<ExchangeWithDetailResponse> getExchangesByUserId(Integer limit, Integer offset, String sortProps, String sortDirection) {
        var user = authUtils.getAuthenticatedUser();
        return exchangeRepository.findAllByRequestUserIdEqualsOrUserIdEquals(user.getUserId()
                        , user.getUserId(),
                        PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(sortDirection), sortProps)))
                .map(ExchangeMapper.INSTANCE::toExchangeWithDetailResponse)
                .map(exchangeWithDetailResponse -> {
                    CompletableFuture<Void> future3 = CompletableFuture.runAsync(()
                            -> exchangeWithDetailResponse.setRequestUser(
                            userService.getUserById(exchangeWithDetailResponse.getRequestUserId())));
                    CompletableFuture<Void> future4 = CompletableFuture.runAsync(()
                            -> exchangeWithDetailResponse.setUser(
                            userService.getUserById(exchangeWithDetailResponse.getUserId())));
                    try {
                        CompletableFuture.allOf(future3, future4).get();
                    } catch (InterruptedException | ExecutionException e) {
                        log.error(e.getMessage());
                    }
                    exchangeWithDetailResponse.setAvailableTime(
                            ApartmentForRentMapper.INSTANCE.toDtoResponse(
                                    availableTimeRepository.findApartmentForRentByCoOwnerIdIgnoreStatus(
                                                    exchangeWithDetailResponse.getAvailableTimeId())
                                            .orElse(null)));
                    exchangeWithDetailResponse.setRequestAvailableTime(
                            ApartmentForRentMapper.INSTANCE.toDtoResponse(
                                    availableTimeRepository.findApartmentForRentByCoOwnerIdIgnoreStatus(
                                                    exchangeWithDetailResponse.getRequestAvailableTimeId())
                                            .orElse(null)));
                    return exchangeWithDetailResponse;
                });
    }
}
