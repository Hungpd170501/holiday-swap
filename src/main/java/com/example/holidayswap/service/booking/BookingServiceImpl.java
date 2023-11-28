package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.request.notification.NotificationRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingDetailResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryDetailBookingOwnerResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.booking.UserOfBookingRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.service.notification.PushNotificationService;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.utils.Helper;
import com.example.holidayswap.utils.RedissonLockUtils;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor

public class BookingServiceImpl implements IBookingService {
    private final AvailableTimeRepository availableTimeRepository;
    private final BookingRepository bookingRepository;
    private final ITransferPointService transferPointService;
    private final IUserOfBookingService userOfBookingService;
    private final UserOfBookingRepository userOfBookingRepository;
    private final PushNotificationService pushNotificationService;


    @Override
    @Transactional
    public EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException {

        if (bookingRequest.getCheckInDate().compareTo(bookingRequest.getCheckOutDate()) >= 0)
            throw new EntityNotFoundException("Check in date must be before check out date");

        List<Booking> checkBookingOverlap;
        Booking checkBooking;
        var notificationRequestForOwner = new NotificationRequest();
        var notificationRequestForUserBooking = new NotificationRequest();
        LocalDate localDateCheckin;
        LocalDate localDateCheckout;
        long days;
        RLock fairLock = RedissonLockUtils.getFairLock("booking-" + bookingRequest.getAvailableTimeId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                Thread.sleep(3000);
                // check List AvailableTime of this apartment
//                Double amount = 0.0;
//                Date intersection_date = bookingRequest.getCheckOutDate();
                AvailableTime availableTime = availableTimeRepository.findAvailableTimeByIdAndStartTimeAndEndTime(bookingRequest.getAvailableTimeId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate()).orElseThrow(() -> new EntityNotFoundException("This availableTime not available in this time"));

//                 TODO: check booking of this apartment
                checkBookingOverlap = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDate(bookingRequest.getAvailableTimeId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
                checkBooking = bookingRepository.checkBookingIsAvailableByCheckinDateAndCheckoutDateAndAvailableId(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getAvailableTimeId());

                if (!checkBookingOverlap.isEmpty() || checkBooking != null)
                    throw new EntityNotFoundException("This apartment is not available in this time");


                // TODO: create booking
                localDateCheckin = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getCheckInDate()));
                localDateCheckout = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingRequest.getCheckOutDate()));
                days = ChronoUnit.DAYS.between(localDateCheckin, localDateCheckout);

                Booking booking = new Booking();
                booking.setCheckInDate(bookingRequest.getCheckInDate());
                booking.setCheckOutDate(bookingRequest.getCheckOutDate());
                booking.setUserBookingId(bookingRequest.getUserId());
                booking.setOwnerId(availableTime.getTimeFrame().getCoOwner().getId().getUserId());
                booking.setAvailableTimeId(bookingRequest.getAvailableTimeId());
                booking.setAvailableTime(availableTime);
                booking.setTotalDays(days);
                booking.setPrice(days * availableTime.getPricePerNight());
                booking.setCommission(booking.getPrice() * 10 / 100);
                booking.setDateBooking(Helper.getCurrentDate());
                booking.setActualPrice(booking.getPrice() - booking.getCommission());
                booking.setStatus(EnumBookingStatus.BookingStatus.SUCCESS);
                bookingRepository.save(booking);
                userOfBookingService.saveUserOfBooking(booking.getId(), bookingRequest.getUserOfBookingRequests());


                //TODO trừ point trong ví

                transferPointService.payBooking(booking);
                //create notification for user booking
                notificationRequestForUserBooking.setSubject("Booking Success");
                notificationRequestForUserBooking.setContent("Booking Apartment " + booking.getAvailableTime().getTimeFrame().getCoOwner().getId().getRoomId() + " of resort " + booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate());
                notificationRequestForUserBooking.setToUserId(bookingRequest.getUserId());
                pushNotificationService.CreateNotification(notificationRequestForUserBooking);
                //create notification for owner
                notificationRequestForOwner.setSubject("Booking Apartment + " + booking.getActualPrice() + " point");
                notificationRequestForOwner.setContent("Booking Apartment " + booking.getAvailableTime().getTimeFrame().getCoOwner().getId().getRoomId() + " of resort " + booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName() + " book from" + booking.getCheckInDate() + " to " + booking.getCheckOutDate());
                notificationRequestForOwner.setToUserId(booking.getAvailableTime().getTimeFrame().getCoOwner().getId().getUserId());
                pushNotificationService.CreateNotification(notificationRequestForOwner);
                return EnumBookingStatus.BookingStatus.SUCCESS;
            } finally {
                fairLock.unlock();
            }
        }
        return EnumBookingStatus.BookingStatus.FAILED;
    }

    @Override
    public List<HistoryBookingResponse> historyBookingUserLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        List<HistoryBookingResponse> historyBookingResponses = new ArrayList<>();
        List<Booking> userBooking = bookingRepository.findAllByUserId(user.getUserId());
        if (userBooking.size() > 0) {
            for (Booking booking : userBooking) {
//                boolean isRating = booking.getRating() != null ? false : true;
                historyBookingResponses.add(
                        new HistoryBookingResponse(
                                booking.getId(),
                                booking.getCheckInDate(),
                                booking.getCheckOutDate(),
                                "check",
                                booking.getAvailableTime().getTimeFrame().getCoOwner().getId().getRoomId(),
                                booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName(),
                                booking.getStatus().name(), booking.getPrice(),
                                booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyImages().get(0).getLink(),
//                                isRating,
                                booking.getAvailableTimeId()));
            }
        }
        return historyBookingResponses;
    }

    @Override
    public HistoryBookingDetailResponse historyBookingDetail(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        var listUserOfBookingEntity = userOfBookingRepository.findAllByBookingId(bookingId);
        var historyBookingDetailResponse = new HistoryBookingDetailResponse();

        historyBookingDetailResponse.setResortName(booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName());
        historyBookingDetailResponse.setDateCheckIn(booking.getCheckInDate());
        historyBookingDetailResponse.setDateCheckOut(booking.getCheckOutDate());
        historyBookingDetailResponse.setRoomId(booking.getAvailableTime().getTimeFrame().getRoomId());
        historyBookingDetailResponse.setPrice(booking.getPrice());
        historyBookingDetailResponse.setNumberOfGuest(booking.getUserOfBookings().size());
        historyBookingDetailResponse.setOwnerEmail(booking.getAvailableTime().getTimeFrame().getCoOwner().getUser().getEmail());
        historyBookingDetailResponse.setStatus(booking.getStatus().name());
        historyBookingDetailResponse.setPropertyName(booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyName());
        historyBookingDetailResponse.setUserOfBooking(listUserOfBookingEntity);
        historyBookingDetailResponse.setAvailableTimeId(booking.getAvailableTimeId());
        historyBookingDetailResponse.setPropertyImage(booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyImages().get(0).getLink());
//        historyBookingDetailResponse.setRating(booking.getRating() != null ? false : true);

        return historyBookingDetailResponse;
    }

    @Override
    public List<HistoryBookingResponse> historyBookingOwnerLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;

        List<HistoryBookingResponse> historyBookingResponses = new ArrayList<>();
        var bookingList = bookingRepository.findAllByOwnerLogin(user.getUserId());
        if (bookingList.size() > 0) {
            for (Booking booking : bookingList) {
                historyBookingResponses.add(new HistoryBookingResponse(booking.getId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(), "check",
                        booking.getAvailableTime().getTimeFrame().getCoOwner().getId().getRoomId(),
                        booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName(),
                        booking.getStatus().name(), booking.getActualPrice(),
                        booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyImages().get(0).getLink(),
//                        booking.getRating() != null ? true : false,
                        booking.getAvailableTimeId()));
            }

        }
        return historyBookingResponses;
    }

    @Override
    public HistoryDetailBookingOwnerResponse historyBookingDetailOwner(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        var listUserOfBookingEntity = userOfBookingRepository.findAllByBookingId(bookingId);
        var historyBookingDetailResponse = new HistoryDetailBookingOwnerResponse();

        historyBookingDetailResponse.setResortName(booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getResort().getResortName());
        historyBookingDetailResponse.setDateCheckIn(booking.getCheckInDate());
        historyBookingDetailResponse.setDateCheckOut(booking.getCheckOutDate());
        historyBookingDetailResponse.setRoomId(booking.getAvailableTime().getTimeFrame().getCoOwner().getId().getRoomId());
        historyBookingDetailResponse.setPrice(booking.getPrice());
        historyBookingDetailResponse.setNumberOfGuest(booking.getUserOfBookings().size());
        historyBookingDetailResponse.setMemberBookingEmail(booking.getUser().getEmail());
        historyBookingDetailResponse.setStatus(booking.getStatus().name());
        historyBookingDetailResponse.setPropertyName(booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyName());
        historyBookingDetailResponse.setCommission(booking.getCommission() + "%");
        historyBookingDetailResponse.setTotal(booking.getActualPrice());
        historyBookingDetailResponse.setUserOfBooking(listUserOfBookingEntity);
        historyBookingDetailResponse.setAvailableTimeId(booking.getAvailableTimeId());
        historyBookingDetailResponse.setPropertyImage(booking.getAvailableTime().getTimeFrame().getCoOwner().getProperty().getPropertyImages().get(0).getLink());
//        historyBookingDetailResponse.setRating(booking.getRating() != null ? true : false);

        return historyBookingDetailResponse;
    }
}
