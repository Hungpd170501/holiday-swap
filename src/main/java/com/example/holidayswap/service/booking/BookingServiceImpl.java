package com.example.holidayswap.service.booking;

import com.example.holidayswap.domain.dto.request.booking.BookingRequest;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.BookingDetail;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.vacation.TimeOffDeposit;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.repository.booking.BookingDetailRepository;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.vacation.TimeOffDepositRepository;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.example.holidayswap.utils.RedissonLockUtils;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor

public class BookingServiceImpl implements IBookingService {

    private final TimeOffDepositRepository timeOffDepositRepository;

    private final BookingRepository bookingRepository;

    private final ITransferPointService transferPointService;

    private final BookingDetailRepository bookingDetailRepository;


    @Override
    @Transactional
    public EnumBookingStatus.BookingStatus createBooking(BookingRequest bookingRequest) throws InterruptedException {
        List<Booking> checkBookingOverlap;
        RLock fairLock = RedissonLockUtils.getFairLock("booking-" + bookingRequest.getPropertyId() + "-" + bookingRequest.getRoomId());
        boolean tryLock = fairLock.tryLock(10, 10, TimeUnit.SECONDS);
        if (tryLock) {
            try {
                Thread.sleep(3000);
                // check List timeOffDeposit of this apartment
                TimeOffDeposit timeDepositeCheck = null;
                Double amount = 0.0;
                TimeOffDeposit timeDepositeLast;
                Date intersection_date = bookingRequest.getCheckOutDate();
                List<TimeOffDeposit> timeOffDeposits = timeOffDepositRepository.findAllByPropertyIdAndRoomIdBetweenDate(bookingRequest.getPropertyId(), bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

                if (timeOffDeposits.size() == 0) {
                    throw new EntityNotFoundException("This apartment is not available in this time");
                }
                timeDepositeLast = timeOffDeposits.get(timeOffDeposits.size() - 1);
                if (timeDepositeLast.getEndTime().before(bookingRequest.getCheckOutDate())) {
                    throw new EntityNotFoundException("This apartment is not available in this time");
                }
                if (timeDepositeLast.getStartTime().compareTo(bookingRequest.getCheckOutDate()) == 0) {
                    timeOffDeposits.remove(timeDepositeLast);
                    if (timeOffDeposits.size() == 0)
                        throw new EntityNotFoundException("This apartment is not available in this time");
                }
                if (timeOffDeposits.get(0).getEndTime().compareTo(bookingRequest.getCheckInDate()) == 0) {
                    timeOffDeposits.remove(0);
                    if (timeOffDeposits.size() == 0)
                        throw new EntityNotFoundException("This apartment is not available in this time");
                }
                // TODO: check booking of this apartment
                checkBookingOverlap = bookingRepository.checkListBookingByCheckinDateAndCheckoutDateAndRoomIdAndPropertyId(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), bookingRequest.getRoomId(), bookingRequest.getPropertyId());
                if (!checkBookingOverlap.isEmpty()) {
                    throw new EntityNotFoundException("This apartment is not available in this time");
                }

                for (TimeOffDeposit timeOffDeposit : timeOffDeposits) {
                    if (timeDepositeCheck != null) {
                        if (timeOffDeposit.getStartTime().compareTo(timeDepositeCheck.getEndTime()) != 0) {
                            throw new EntityNotFoundException("This apartment is not available in this time");
                        }
                    }
                    timeDepositeCheck = timeOffDeposit;
                }


                // TODO: create booking

                Booking booking = new Booking();
                booking.setCheckInDate(bookingRequest.getCheckInDate());
                booking.setCheckOutDate(bookingRequest.getCheckOutDate());
                booking.setRoomId(bookingRequest.getRoomId());
                booking.setPropertyId(bookingRequest.getPropertyId());
                booking.setUserId(bookingRequest.getUserId());
                booking.setStatus(EnumBookingStatus.BookingStatus.PENDING);

                bookingRepository.save(booking);
                // TODO: createBooking detail

                for (TimeOffDeposit timeOffDeposit : timeOffDeposits) {

                    BookingDetail bookingDetail = new BookingDetail();
                    bookingDetail.setBookId(booking.getId());
                    bookingDetail.setPropertyId(bookingRequest.getPropertyId());
                    bookingDetail.setRoomId(bookingRequest.getRoomId());
                    bookingDetail.setUserId(timeOffDeposit.getVacation().getUserId());
                    bookingDetail.setCheckInDate(bookingRequest.getCheckInDate());
                    if (timeOffDeposits.size() > 1) {
                        bookingRequest.setCheckInDate(timeOffDeposit.getEndTime());
                        intersection_date = timeOffDeposit.getEndTime();
                    }
                    if (timeOffDeposit.equals(timeOffDeposits.get(timeOffDeposits.size() - 1))) {
                        intersection_date = bookingRequest.getCheckOutDate();
                    }
                    bookingDetail.setCheckOutDate(intersection_date);

                    LocalDate localDateCheckin = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingDetail.getCheckInDate()));
                    LocalDate localDateCheckout = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(bookingDetail.getCheckOutDate()));
                    long days = ChronoUnit.DAYS.between(localDateCheckin, localDateCheckout);
                    bookingDetail.setTotalPoint((double) days * timeOffDeposit.getPricePerNight());
                    bookingDetail.setNumberOfGuests(4);
                    amount += bookingDetail.getTotalPoint();
                    bookingDetailRepository.save(bookingDetail);
                }

                //TODO trừ point trong ví


                transferPointService.payBooking(bookingRequest.getUserId(), booking.getId(), amount);

                return EnumBookingStatus.BookingStatus.SUCCESS;
            } finally {
                fairLock.unlock();
            }
        }
        return EnumBookingStatus.BookingStatus.SUCCESS;
    }
}
