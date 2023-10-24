package com.example.holidayswap.service.property;

import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ApartmentForRentMapper;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApartmentForRentServiceImpl implements ApartmentForRentService {
    private final InRoomAmenityTypeServiceImpl inRoomAmenityTypeService;
    private final PropertyImageServiceImpl propertyImageService;
    private final AvailableTimeRepository availableTimeRepository;

    @Override
    public Page<ApartmentForRentResponse> gets(Date checkIn, Date checkOut, double min, double max, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable) {
        var dto = availableTimeRepository.findApartmentForRent(checkIn, checkOut, min, max, listOfInRoomAmenity, listOfPropertyView, listOfPropertyType, pageable);

        var response = dto.map(ApartmentForRentMapper.INSTANCE::toDtoResponse);
        response.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getProperty().getId());
            var propertyImages = propertyImageService.gets(e.getProperty().getId());
            e.getProperty().setPropertyImage(propertyImages);
            e.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
        });
        return response;
    }

    @Override
    public ApartmentForRentResponse get(Long availableId) {
        var dto = availableTimeRepository.findApartmentForRentByCoOwnerId(availableId).orElseThrow(() -> new EntityNotFoundException("No property for rent found."));
        var response = ApartmentForRentMapper.INSTANCE.toDtoResponse(dto);
        {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(response.getProperty().getId());
            var propertyImages = propertyImageService.gets(response.getProperty().getId());
            response.getProperty().setPropertyImage(propertyImages);
            response.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
        }
        return response;
    }
}
