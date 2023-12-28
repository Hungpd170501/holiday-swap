package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.ApartmentForRentResponse;
import com.example.holidayswap.domain.dto.response.property.ResortApartmentForRentResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ApartmentForRentMapper;
import com.example.holidayswap.domain.mapper.property.ResortApartmentForRentMapper;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeServiceImpl;
import com.example.holidayswap.service.property.rating.RatingServiceImpl;
import com.example.holidayswap.utils.AuthUtils;
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
    private final ResortRepository resortRepository;
    private final ResortApartmentForRentMapper resortApartmentForRentMapper;
    private final AuthUtils authUtils;
    private final RatingServiceImpl ratingService;

    @Override
    public Page<ApartmentForRentResponse> gets(String locationName, Long resortId, Date checkIn, Date checkOut, Long min, Long max, int guest, int numberBedsRoom, int numberBathRoom, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable) {
        var user = authUtils.GetUser();
        Long userId = null;
        if (user.isPresent()) userId = user.get().getUserId();
        var dto = availableTimeRepository.findApartmentForRent(StringUtils.stripAccents(locationName).toUpperCase(),
                resortId, checkIn, checkOut, min, max, guest, numberBedsRoom, numberBathRoom, listOfInRoomAmenity,
                listOfPropertyView, listOfPropertyType, userId, pageable);
        var response = dto.map(ApartmentForRentMapper.INSTANCE::toDtoResponse);
        response.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getAvailableTime().getCoOwner().getProperty().getId());
            e.getAvailableTime().getCoOwner().getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
//            var propertyImages = propertyImageService.gets(e.getProperty().getId());
//            e.getProperty().setPropertyImage(propertyImages);
            e.getAvailableTime().getCoOwner().getProperty().getPropertyImages().stream().filter((img) -> !img.isDeleted());
            e.getAvailableTime().getCoOwner().getProperty().setRating(ratingService.getRatingOfProperty(e.getAvailableTime().getCoOwner().getProperty().getId(), e.getAvailableTime().getCoOwner().getRoomId()));
        });
        return response;
    }

    @Override
    public Page<ResortApartmentForRentResponse> getsResort(String locationName, Date checkIn, Date checkOut, Long min, Long max, int guest, int numberBedsRoom, int numberBathRoom, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable) {
        var user = authUtils.GetUser();
        Long userId = null;
        if (user.isPresent()) userId = user.get().getUserId();
        var dto = resortRepository.findResort(StringUtils.stripAccents(locationName).toUpperCase(), checkIn, checkOut, min, max, guest, numberBedsRoom, numberBathRoom, listOfInRoomAmenity, listOfPropertyView, listOfPropertyType, userId, pageable);
        var response = dto.map(resortApartmentForRentMapper::toDtoResponse);
        response.forEach(e -> {

        });
        return response;
    }

    @Override
    public ApartmentForRentResponse get(Long availableId) {
        var dto = availableTimeRepository.findApartmentForRentByCoOwnerId(availableId).orElseThrow(() -> new EntityNotFoundException("No property for rent found."));
        var response = ApartmentForRentMapper.INSTANCE.toDtoResponse(dto);
        {
            var timeHasBooking = bookingRepository.findAllByAvailableTimeIdAndStatus(dto.getAvailableTime().getId(), EnumBookingStatus.BookingStatus.SUCCESS);
            response.setTimeHasBooked(timeHasBooking);
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(response.getAvailableTime().getCoOwner().getProperty().getId());
//            var propertyImages = propertyImageService.gets(response.getAvailableTime().getCoOwner().getProperty().getId());
            response.getAvailableTime().getCoOwner().getProperty().getPropertyImages().stream().filter((img) -> !img.isDeleted());
            response.getAvailableTime().getCoOwner().getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
//            response.getProperty().setRating(ratingRepository.calculateRating(dto.getProperty().getId(), dto.getCoOwnerId().getRoomId()));
            response.getAvailableTime().getCoOwner().getProperty().setRating(ratingService.getRatingOfProperty(response.getAvailableTime().getCoOwner().getProperty().getId(), response.getAvailableTime().getCoOwner().getRoomId()));
        }
        return response;
    }

    @Override
    public Page<ApartmentForRentResponse> getByUserId(Long userId, Pageable pageable) {
        var dto = availableTimeRepository.findApartmentForRentByUserId(userId, pageable);
        var response = dto.map(ApartmentForRentMapper.INSTANCE::toDtoResponse);
        return response;
    }
}
