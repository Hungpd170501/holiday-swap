package com.example.holidayswap.service.payment;

import com.example.holidayswap.domain.dto.response.payment.HistoryTransactionResponse;

import java.text.ParseException;
import java.util.List;

public interface IHistoryTransactionService {
    List<HistoryTransactionResponse> getHistoryTransactionByUserId(long id) throws ParseException;
}
