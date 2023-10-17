package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.response.property.Room;
import com.example.holidayswap.service.property.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rooms")
public class RoomsController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<Page<Room>> gets(@RequestParam(value = "checkIn", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkIn,
                                           @RequestParam(value = "checkOut", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOut,
                                           @RequestParam(name = "min") double min,
                                           @RequestParam(name = "max") double max,

                                           @RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "roomId") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponse = roomService.gets(checkIn, checkOut, min, max, pageable);
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> get(@PathVariable("roomId") String roomId) {
        var dtoResponse = roomService.get(roomId);
        return ResponseEntity.ok(dtoResponse);
    }
}
