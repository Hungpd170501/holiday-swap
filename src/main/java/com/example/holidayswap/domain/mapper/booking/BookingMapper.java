package com.example.holidayswap.domain.mapper.booking;

import com.example.holidayswap.domain.dto.response.booking.BookingCoOwnerResponse;
import com.example.holidayswap.domain.entity.booking.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    BookingCoOwnerResponse toDtoResponse(Booking entity);

    Booking toEntity(BookingCoOwnerResponse dtoRequest);
}