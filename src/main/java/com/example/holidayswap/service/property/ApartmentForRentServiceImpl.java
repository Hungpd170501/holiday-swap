package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ApartmentForRentMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeServiceImpl;
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
    private final AvailableTimeRepository availableTimeRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Page<ApartmentForRentResponse> gets(String locationName, Long resortId, Date checkIn, Date checkOut, Long min, Long max, int guest, int numberBedsRoom, int numberBathRoom, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable) {
        var dto = availableTimeRepository.findApartmentForRent(StringUtils.stripAccents(locationName).toUpperCase(), resortId, checkIn, checkOut, min, max, guest, numberBedsRoom, numberBathRoom, listOfInRoomAmenity, listOfPropertyView, listOfPropertyType, pageable);
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
            var timeHasBooking = bookingRepository.findAllByAvailableTimeIdAndStatus(dto.getAvailableTime().getId(),
                    EnumBookingStatus.BookingStatus.SUCCESS);
            response.setTimeHasBooked(timeHasBooking);
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(response.getProperty().getId());
            var propertyImages = propertyImageService.gets(response.getProperty().getId());
            response.getProperty().setPropertyImage(propertyImages);
            response.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
        }
        return response;
    }
}
