package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.dto.request.booking.UserOfBookingRequest;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingDetailResponse;
import com.example.holidayswap.domain.dto.response.booking.HistoryBookingResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.BookingDetail;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.Property;
import com.example.holidayswap.domain.entity.property.timeFrame.AvailableTime;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.booking.BookingDetailRepository;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.booking.UserOfBookingRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.utils.RedissonLockUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.redisson.api.RLock;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor

public class BookingServiceImpl implements IBookingService {

    private final AvailableTimeRepository availableTimeRepository;

    private final BookingRepository bookingRepository;

    private final ITransferPointService transferPointService;

    private final BookingDetailRepository bookingDetailRepository;

    private final PropertyRepository propertyRepository;

    private final IUserOfBookingService userOfBookingService;

    private final UserOfBookingRepository userOfBookingRepository;


    @Override
    @Transactional
    public EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException {
        List<Booking> checkBookingOverlap;
        RLock fairLock = RedissonLockUtils.getFairLock("booking-" + bookingRequest.getPropertyId() + "-" + bookingRequest.getRoomId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                // check List AvailableTime of this apartment
                AvailableTime timeDepositeCheck = null;
                Double amount = 0.0;
                AvailableTime timeDepositeLast;
                Date intersection_date = bookingRequest.getCheckOutDate();
                List<AvailableTime> AvailableTimes = availableTimeRepository.findAllByPropertyIdAndRoomIdBetweenDate(bookingRequest.getPropertyId(), bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

                if (AvailableTimes.size() == 0) {
                    throw new EntityNotFoundException("This apartment is not available in this time");
                }
//                 TODO: check booking of this apartment
                checkBookingOverlap = bookingRepository.checkListBookingByCheckinDateAndCheckoutDateAndRoomIdAndPropertyId(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomId(), bookingRequest.getPropertyId());
                if (!checkBookingOverlap.isEmpty()) {
                    throw new EntityNotFoundException("This apartment is not available in this time");
                }

                for (AvailableTime AvailableTime : AvailableTimes) {
                    if (timeDepositeCheck != null) {
                        if (AvailableTime.getStartTime().compareTo(timeDepositeCheck.getEndTime()) != 0) {
                            throw new EntityNotFoundException("This apartment is not available in this time");
                        }
                    }
                    timeDepositeCheck = AvailableTime;
                }


                // TODO: create booking

                Booking booking = new Booking();
                booking.setCheckInDate(bookingRequest.getCheckInDate());
                booking.setCheckOutDate(bookingRequest.getCheckOutDate());
                booking.setRoomId(bookingRequest.getRoomId());
                booking.setPropertyId(bookingRequest.getPropertyId());
                booking.setUserId(bookingRequest.getUserId());
                booking.setStatus(EnumBookingStatus.BookingStatus.PENDING);
                booking.setPrice(0D);

                bookingRepository.save(booking);

                userOfBookingService.saveUserOfBooking(booking.getId(),bookingRequest.getUserOfBookingRequests());
                // TODO: createBooking detail

                for (AvailableTime AvailableTime : AvailableTimes) {

                    BookingDetail bookingDetail = new BookingDetail();
                    bookingDetail.setBookId(booking.getId());
                    bookingDetail.setPropertyId(bookingRequest.getPropertyId());
                    bookingDetail.setRoomId(bookingRequest.getRoomId());
                    bookingDetail.setUserId(AvailableTime.getTimeFrame().getUserId());
                    bookingDetail.setCheckInDate(bookingRequest.getCheckInDate());
                    if (AvailableTimes.size() > 1) {
                        bookingRequest.setCheckInDate(AvailableTime.getEndTime());
                        intersection_date = AvailableTime.getEndTime();
                    }
                    if (AvailableTime.equals(AvailableTimes.get(AvailableTimes.size() - 1))) {
                        intersection_date = bookingRequest.getCheckOutDate();
                    }
                    bookingDetail.setCheckOutDate(intersection_date);

                    LocalDate localDateCheckin = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingDetail.getCheckInDate()));
                    LocalDate localDateCheckout = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingDetail.getCheckOutDate()));
                    long days = ChronoUnit.DAYS.between(localDateCheckin, localDateCheckout);
                    bookingDetail.setTotalPoint((double) days * AvailableTime.getPricePerNight());
                    booking.setPrice(booking.getPrice() + bookingDetail.getTotalPoint());
                    bookingDetail.setNumberOfGuests(bookingRequest.getUserOfBookingRequests().size());
                    amount += bookingDetail.getTotalPoint();
                    bookingDetailRepository.save(bookingDetail);
                }


                //TODO trừ point trong ví


                transferPointService.payBooking(bookingRequest.getUserId(), booking.getId(), amount);

                booking.setStatus(EnumBookingStatus.BookingStatus.SUCCESS);
                bookingRepository.save(booking);

                return EnumBookingStatus.BookingStatus.SUCCESS;
            } finally {
                fairLock.unlock();
            }
        }
        return EnumBookingStatus.BookingStatus.SUCCESS;
    }

    @Override
    public List<HistoryBookingResponse> historyBookingUserLogin() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        List<HistoryBookingResponse> historyBookingResponses = new ArrayList<>();
        List<Booking> bookingList = bookingRepository.findAll();
        List<Booking> userBooking = bookingRepository.findAllByUserId(user.getUserId());
        if (userBooking.size() > 0) {
            for (Booking booking : userBooking){
                Property property = propertyRepository.findById(booking.getPropertyId()).get();
                historyBookingResponses.add(new HistoryBookingResponse(booking.getId(),booking.getCheckInDate(),booking.getCheckOutDate(),"check",booking.getRoomId(),property.getResort().getResortName(),booking.getStatus().name(),booking.getPrice()));
            }
        }
        return historyBookingResponses;
    }

    @Override
    public HistoryBookingDetailResponse historyBookingDetail(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        var listUserOfBookingEntity = userOfBookingRepository.findAllByBookingId(bookingId);

        var historyBookingDetailResponse = new HistoryBookingDetailResponse();
        historyBookingDetailResponse.setResortName(booking.getProperty().getResort().getResortName());
        historyBookingDetailResponse.setDateCheckIn(booking.getCheckInDate());
        historyBookingDetailResponse.setDateCheckOut(booking.getCheckOutDate());
        historyBookingDetailResponse.setRoomId(booking.getRoomId());
        historyBookingDetailResponse.setPrice(booking.getPrice());
        historyBookingDetailResponse.setNumberOfGuest(booking.getBookingDetail().get(0).getNumberOfGuests());
        historyBookingDetailResponse.setOwnerEmail(booking.getBookingDetail().get(0).getCoOwner().getUser().getEmail());
        historyBookingDetailResponse.setStatus(booking.getStatus().name());
        historyBookingDetailResponse.setPropertyName(booking.getProperty().getPropertyName());
        historyBookingDetailResponse.setUserOfBooking(listUserOfBookingEntity);

        return historyBookingDetailResponse;
    }
}
