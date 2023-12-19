package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.booking.Booking;
import com.example.holidayswap.domain.entity.payment.AllLog;

import java.util.List;

public interface ITransferPointService {
    public TransferResponse transferPoint(long from, long to, Double amount) throws InterruptedException;

    List<TransactionTranferPointResponse> getTransactionTranferPointByUserId(Long userId);

    List<TransactionTranferPointResponse> convertAllLogToTransactionTranferPointResponse(List<AllLog> allLogs, Long userId);
    TransferResponse payBooking(Booking booking) throws InterruptedException;

    TransferResponse returnPoint(long from, long to, Double amount,Double commision) throws InterruptedException;

    void refundPointBookingToOwner(Long bookingId) throws InterruptedException;

    void refundPointBookingToUser(Long bookingId) throws InterruptedException;
}
