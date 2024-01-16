package com.example.holidayswap.domain.mapper.exchange;

import com.example.holidayswap.domain.dto.request.exchange.ExchangeCreatingRequest;
import com.example.holidayswap.domain.dto.response.exchange.ExchangeResponse;
import com.example.holidayswap.domain.entity.exchange.Exchange;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExchangeMapper {
    ExchangeMapper INSTANCE = Mappers.getMapper(ExchangeMapper.class);

    Exchange toExchangeEntity(ExchangeCreatingRequest exchangeCreatingRequest);

    ExchangeResponse toExchangeResponse(Exchange exchange);
}
