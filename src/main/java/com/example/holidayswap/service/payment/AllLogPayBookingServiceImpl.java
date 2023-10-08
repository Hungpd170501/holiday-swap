package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.entity.payment.AllLogPayBooking;
import com.example.holidayswap.domain.entity.payment.EnumPaymentStatus;
import com.example.holidayswap.repository.payment.AllLogPayBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AllLogPayBookingServiceImpl implements IAllLogPayBookingService {

    @Autowired
    private AllLogPayBookingRepository allLogPayBookingRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(long fromMemberId, long toBookingId, Double amount, EnumPaymentStatus.BankCodeError resultCode, String detail, String createdOn, Double fromBalance) {
        AllLogPayBooking allLogPayBooking = new AllLogPayBooking();
        allLogPayBooking.setToBookingId(toBookingId);
        allLogPayBooking.setFromMemberId(fromMemberId);
        allLogPayBooking.setAmount(amount);
        allLogPayBooking.setResultCode(resultCode);
        allLogPayBooking.setDetail(detail);
        allLogPayBooking.setCreatedOn(createdOn);
        allLogPayBooking.setFromBalance(fromBalance);

        allLogPayBookingRepository.save(allLogPayBooking);

    }

    @Override
    public List<AllLogPayBooking> getAllLogPayBookingByUserId(long userId) {
        return allLogPayBookingRepository.findAllByFromMemberId(userId);
    }
}
