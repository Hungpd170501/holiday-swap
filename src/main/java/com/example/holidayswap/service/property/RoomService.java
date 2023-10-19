package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface RoomService {
    Page<Room> gets(Date checkIn, Date checkOut, double min, double max, Pageable pageable);

    Room get(String roomId);
}
