package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;

import java.util.List;

public interface ITransferPointService {
    public TransferResponse transferPoint(long from, long to, long amount);

    List<TransactionTranferPointResponse> getTransactionTranferPointByUserId(Long userId);
}
