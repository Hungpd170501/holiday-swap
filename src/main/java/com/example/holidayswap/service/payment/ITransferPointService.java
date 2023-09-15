package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.TransferResponse;

public interface ITransferPointService {
    public TransferResponse transferPoint(long from, long to, long amount);
}
