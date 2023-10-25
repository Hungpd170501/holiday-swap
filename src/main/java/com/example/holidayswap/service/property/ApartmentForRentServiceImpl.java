package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerId;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ApartmentForRentMapper;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeServiceImpl;
import com.example.holidayswap.service.property.timeFame.AvailableTimeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApartmentForRentServiceImpl implements ApartmentForRentService {
    private final InRoomAmenityTypeServiceImpl inRoomAmenityTypeService;
    private final PropertyImageServiceImpl propertyImageService;
    private final CoOwnerRepository coOwnerRepository;
    private final AvailableTimeService availableTimeService;
    private final ApartmentForRentMapper roomMapper;
    private final AvailableTimeRepository availableTimeRepository;

    @Override
    public Page<ApartmentForRentResponse> gets(String locationName, Date checkIn, Date checkOut, double min, double max, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable) {
        var dto = availableTimeRepository.findApartmentForRent(StringUtils.stripAccents(locationName).toUpperCase(), checkIn, checkOut, min, max, listOfInRoomAmenity, listOfPropertyView, listOfPropertyType, pageable);

        var response = dto.map(roomMapper::toDtoResponse);
        response.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getProperty().getId());
            var propertyImages = propertyImageService.gets(e.getProperty().getId());
            e.getProperty().setPropertyImage(propertyImages);
            e.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
        });
        return response;
    }

    @Override
    public ApartmentForRentResponse get(CoOwnerId coOwnerId) {
        var dto = availableTimeRepository.findApartmentForRentByCoOwnerId(coOwnerId.getPropertyId(), coOwnerId.getUserId(), coOwnerId.getRoomId()).orElseThrow(() -> new EntityNotFoundException("No property for rent found."));
        var response = roomMapper.toDtoResponse(dto);
        {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(response.getProperty().getId());
            var propertyImages = propertyImageService.gets(response.getProperty().getId());
            response.getProperty().setPropertyImage(propertyImages);
            response.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
        }
        return response;
    }
}
