package com.example.holidayswap.controller.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.service.property.ApartmentForRentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/apartment-for-rent")
public class ApartmentsForRentController {
    private final ApartmentForRentService roomService;

    @GetMapping
    public ResponseEntity<Page<ApartmentForRentResponse>> gets(@RequestParam(value = "checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkIn,
                                                               @RequestParam(value = "checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOut,
                                                               @RequestParam(name = "min") double min,
                                                               @RequestParam(name = "max") double max,
                                                               @RequestParam(name = "listOfInRoomAmenity", required = false) Set<Long> listOfInRoomAmenity,
                                                               @RequestParam(name = "listOfPropertyView", required = false) Set<Long> listOfPropertyView,
                                                               @RequestParam(name = "listOfPropertyType", required = false) Set<Long> listOfPropertyType,
                                                               @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                               @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        var dtoResponse = roomService.gets(checkIn, checkOut, min, max, listOfInRoomAmenity, listOfPropertyView, listOfPropertyType, pageable);
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping("/detail")
    public ResponseEntity<ApartmentForRentResponse> get(@RequestParam("proeprtyId") Long proeprtyId, @RequestParam("userId") Long userId, @RequestParam("roomId") String roomId) {
        CoOwnerId coOwnerId = new CoOwnerId(proeprtyId, userId, roomId);
        var dtoResponse = roomService.get(coOwnerId);
        return ResponseEntity.ok(dtoResponse);
    }
}
