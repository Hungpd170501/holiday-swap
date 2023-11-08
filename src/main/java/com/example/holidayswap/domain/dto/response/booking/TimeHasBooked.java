package com.example.holidayswap.domain.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TimeHasBooked {
        private Date checkIn;
        private Date checkOut;
}
