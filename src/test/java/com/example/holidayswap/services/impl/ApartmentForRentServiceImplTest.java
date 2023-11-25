package com.example.holidayswap.services.impl;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentDTO;
import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.mapper.property.ApartmentForRentMapper;
import com.example.holidayswap.domain.mapper.property.ResortApartmentForRentMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.rating.RatingRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.service.property.ApartmentForRentServiceImpl;
import com.example.holidayswap.service.property.PropertyImageServiceImpl;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeServiceImpl;
import com.example.holidayswap.utils.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApartmentForRentServiceImplTest {
    ApartmentForRentServiceImpl apartmentForRentServiceImplUnderTest;
    InRoomAmenityTypeServiceImpl inRoomAmenityTypeServiceImplUnderTest;
    PropertyImageServiceImpl propertyImageServiceImplUnderTest;
    AvailableTimeRepository availableTimeRepository;
    BookingRepository bookingRepository;
    ResortRepository resortRepository;
    ResortApartmentForRentMapper resortApartmentForRentMapper;
    AuthUtils authUtils;
    RatingRepository ratingRepository;

    @BeforeEach
    void beforeEach(){

        inRoomAmenityTypeServiceImplUnderTest = mock(InRoomAmenityTypeServiceImpl.class);
        propertyImageServiceImplUnderTest = mock(PropertyImageServiceImpl.class);
        availableTimeRepository = mock(AvailableTimeRepository.class);
        bookingRepository = mock(BookingRepository.class);
        resortRepository = mock(ResortRepository.class);
        resortApartmentForRentMapper = mock(ResortApartmentForRentMapper.class);
        authUtils = mock(AuthUtils.class);
        ratingRepository = mock(RatingRepository.class);
        apartmentForRentServiceImplUnderTest = new ApartmentForRentServiceImpl(inRoomAmenityTypeServiceImplUnderTest, propertyImageServiceImplUnderTest, availableTimeRepository, bookingRepository, resortRepository, resortApartmentForRentMapper, authUtils, ratingRepository);
    }

    @Test
    void gets_ShouldReturnPage_WhenInputIsValid(){
//        var apartmentForRentDTO1 = ApartmentForRentDTO.builder().build();
//        var apartmentForRentDTO2 = ApartmentForRentDTO.builder().build();
//        Page<ApartmentForRentDTO> page = new PageImpl<>(List.of(apartmentForRentDTO1, apartmentForRentDTO2));
//        User user = mock(User.class);
//        var apartmentForRentResponse1 = ApartmentForRentResponse.builder().build();
//        var apartmentForRentResponse2 = ApartmentForRentResponse.builder().build();
//        Page<ApartmentForRentResponse> pageResponse = new PageImpl<>(List.of(apartmentForRentResponse1, apartmentForRentResponse2));
//        when(authUtils.GetUser()).thenReturn(java.util.Optional.of(user));
//        when(user.getUserId()).thenReturn(1L);
//        when(availableTimeRepository.findApartmentForRent("locationName", 0L, null, null, 0L, 0L, 0, 0, 0, null, null, null, 1L, null)).thenReturn(page);
////        when(inRoomAmenityTypeServiceImplUnderTest.gets(0L)).thenReturn(null);
////        when(propertyImageServiceImplUnderTest.gets(0L)).thenReturn(null);
////        when(ratingRepository.calculateRating(0L, "123")).thenReturn(0D);
//        when(page.map(ApartmentForRentMapper.INSTANCE::toDtoResponse)).thenReturn(pageResponse);
//
//        // Setup
//
//        // Run the test
//        apartmentForRentServiceImplUnderTest.gets("locationName", 0L, null, null, 0L, 0L, 0, 0, 0, null, null, null, null);

        // Verify the results
    }

}
