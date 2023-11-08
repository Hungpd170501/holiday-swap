package com.example.holidayswap.domain.dto.response.property;

import com.example.holidayswap.domain.entity.resort.Resort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResortApartmentForRentDTO {
    private Resort resort;
}