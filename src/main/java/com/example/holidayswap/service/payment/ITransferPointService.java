package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.TransactionTranferPointResponse;
import com.example.holidayswap.domain.dto.response.payment.TransferResponse;
import com.example.holidayswap.domain.entity.payment.AllLog;

import java.util.List;

public interface ITransferPointService {
    public TransferResponse transferPoint(long from, long to, long amount) throws InterruptedException;

    List<TransactionTranferPointResponse> getTransactionTranferPointByUserId(Long userId);

    List<TransactionTranferPointResponse> convertAllLogToTransactionTranferPointResponse(List<AllLog> allLogs, Long userId);
}
