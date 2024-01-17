package com.example.holidayswap.service.exchange;

import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeUpdatingRequest;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;

public interface IExchangeService {
    ExchangeResponse create(ExchangeCreatingRequest exchangeCreatingRequest);
    void updateBaseData(Long exchangeId, ExchangeUpdatingRequest exchangeUpdatingRequest);

    void updateNextStatus(Long exchangeId) throws InterruptedException;

    void updatePreviousStatus(Long exchangeId) throws InterruptedException;

    void cancel(Long exchangeId) throws InterruptedException;
}
