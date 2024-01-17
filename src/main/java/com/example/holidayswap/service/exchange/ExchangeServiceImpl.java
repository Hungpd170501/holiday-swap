package com.example.holidayswap.service.exchange;

import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.exchange.Exchange;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.exchange.ExchangeRepository;
import com.example.holidayswap.service.booking.IBookingService;
import com.example.holidayswap.service.payment.ITransferPointService;
import com.google.zxing.WriterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

@Service
@AllArgsConstructor
public class ExchangeServiceImpl implements IExchangeService{
    private final ITransferPointService transferPointService;
    private final ExchangeRepository exchangeRepository;
    private final IBookingService bookingService;
    private final BookingRepository bookingRepository;
    @Override
    public void CreateExchange(Long availableTimeIdOfUser1, int totalMemberOfUser1, Long availableTimeIdOfUser2, int totalMemberOfUser2, Long userIdOfUser1, Long userIdOfUser2, Double priceOfUser1, Double priceOfUser2, Date startDate1, Date endDate1, Date startDate2, Date endDate2){
        Exchange exchange = new Exchange();
        exchange.setAvailableTimeIdOfUser1(availableTimeIdOfUser1);
        exchange.setTotalMemberOfUser1(totalMemberOfUser1);
        exchange.setAvailableTimeIdOfUser2(availableTimeIdOfUser2);
        exchange.setTotalMemberOfUser2(totalMemberOfUser2);
     exchange.setCheckInDateOfUser1(startDate1);
        exchange.setCheckOutDateOfUser1(endDate1);
        exchange.setCheckInDateOfUser2(startDate2);
        exchange.setCheckOutDateOfUser2(endDate2);
        exchange.setUserIdOfUser1(userIdOfUser1);
        exchange.setUserIdOfUser2(userIdOfUser2);
        exchange.setPriceOfUser1(priceOfUser1);
        exchange.setPriceOfUser2(priceOfUser2);

        exchange.setStatus("Phase 1");
        exchangeRepository.save(exchange);
    }

    @Override
    @Transactional
    public void UpdateStatus(Long exchangeId, String status) throws IOException, InterruptedException, WriterException {
        var exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new RuntimeException("Exchange not found"));
if(status.equals("Phase 2")) {
    ExchangeResponse response = bookingService.createExchange(exchange);
    exchange.setBookingIdOfUser1(response.getBookingIdOfUser1());
    exchange.setBookingIdOfUser2(response.getBookingIdOfUser2());

}
if(status.equals("Phase 3")) {
    Long bookingId = exchange.getPriceOfUser1() > exchange.getPriceOfUser2() ? exchange.getBookingIdOfUser1() : exchange.getBookingIdOfUser2();
    Booking checkBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
    transferPointService.payBooking(checkBooking);
    bookingService.updateExchange(exchange, EnumBookingStatus.BookingStatus.SUCCESS);
}
if(status.equals("Cancel")){
    bookingService.updateExchange(exchange, EnumBookingStatus.BookingStatus.CANCELLED);
}
        exchange.setStatus(status);
        exchangeRepository.save(exchange);
    }

    @Override
    @Transactional
    public void UpdateExchange(Long exchangeId, Long availableTimeIdOfUser1, int totalMemberOfUser1, Long availableTimeIdOfUser2, int totalMemberOfUser2, Double priceOfUser1, Double priceOfUser2,Date startDate1, Date endDate1, Date startDate2, Date endDate2) {
        var exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new RuntimeException("Exchange not found"));
        if(!exchange.getStatus().equals("Phase 1")) {
            throw new RuntimeException("Exchange is not in phase 1");
        }
        exchange.setAvailableTimeIdOfUser1(availableTimeIdOfUser1);
        exchange.setTotalMemberOfUser1(totalMemberOfUser1);
        exchange.setAvailableTimeIdOfUser2(availableTimeIdOfUser2);
        exchange.setTotalMemberOfUser2(totalMemberOfUser2);
        exchange.setPriceOfUser1(priceOfUser1);
        exchange.setPriceOfUser2(priceOfUser2);
        exchange.setCheckInDateOfUser1(startDate1);
        exchange.setCheckOutDateOfUser1(endDate1);
        exchange.setCheckInDateOfUser2(startDate2);
        exchange.setCheckOutDateOfUser2(endDate2);
        exchangeRepository.save(exchange);
    }
}
