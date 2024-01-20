package com.example.holidayswap.service.exchange;

import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.request.exchange.ExchangeUpdatingRequest;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeWithDetailResponse;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface IExchangeService {
    ExchangeResponse create(ExchangeCreatingRequest exchangeCreatingRequest);
    void updateBaseData(Long exchangeId, ExchangeUpdatingRequest exchangeUpdatingRequest);

    void updateNextStatus(Long exchangeId) throws InterruptedException, MessagingException, IOException, WriterException;

    void updatePreviousStatus(Long exchangeId) throws InterruptedException;

    void cancel(Long exchangeId) throws InterruptedException;

    ExchangeResponse getExchangeById(Long exchangeId);

    Page<ExchangeWithDetailResponse> getExchangesByUserId(Integer limit, Integer offset, String sortProps, String sortDirection);
}
