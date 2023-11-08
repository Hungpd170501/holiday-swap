package com.example.holidayswap.domain.dto.response.property;

import java.util.List;

import com.example.holidayswap.domain.dto.response.resort.ResortResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResortApartmentForRentResponse {
    private ResortResponse resort;
    private List<ApartmentForRentResponse> apartmentForRentResponseList;
}