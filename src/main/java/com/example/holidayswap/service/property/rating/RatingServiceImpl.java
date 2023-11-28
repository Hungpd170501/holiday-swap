package com.example.holidayswap.service.property.rating;

import com.example.holidayswap.domain.dto.request.property.rating.RatingRequest;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
import com.example.holidayswap.domain.entity.booking.EnumBookingStatus;
import com.example.holidayswap.domain.entity.property.rating.RatingId;
import com.example.holidayswap.domain.entity.property.rating.RatingType;
import com.example.holidayswap.domain.exception.AccessDeniedException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.rating.RatingMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.rating.RatingRepository;
import com.example.holidayswap.repository.property.timeFrame.AvailableTimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final PropertyRepository propertyRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    private static String convertFullName(String userFullName) {
        if (userFullName == null || userFullName.isEmpty()) {
            return "";
        }

        String convertedFullNameBuilder = userFullName.charAt(0) +
                "*".repeat(userFullName.length() - 1);

        return convertedFullNameBuilder;
    }

    @Override
    public Page<RatingResponse> getListRatingByPropertyId(Long propertyId, String roomId, Pageable pageable) {
        var entities = ratingRepository.findAllByPropertyIdAndRoomId(propertyId, roomId, pageable);
        entities.map((element) -> {
            if (element.getRatingType().equals(RatingType.PRIVATE)) {
                String convertedFullName = convertFullName(element.getUser().getFullName());
                element.getUser().setFullName(convertedFullName);
                element.getUser().setUserId(null);
                element.getUser().setEmail(null);
                element.getUser().setAvatar(null);
                element.getUser().setDob(null);
                element.getUser().setFullName(convertedFullName);
            }
            return element;
        });
        var dto = entities.map(ratingMapper::toDtoResponse);
        return dto;
    }

    @Override
    public Double getRatingOfProperty(Long propertyId, String roomId) {
        return ratingRepository.calculateRating(propertyId, roomId);
    }

    @Override
    public RatingResponse getRatingByBookingId(Long bookingId) {
        var e = ratingRepository.findByBookingId(bookingId).orElseThrow(() -> new EntityNotFoundException("Rating " +
                "not found"));
        var rs = ratingMapper.toDtoResponse(e);
        return rs;
    }

    @Override
    public void create(Long bookingId, Long userId, RatingRequest ratingRequest) {
//        isOutOfDateToRating(bookingId);
//        isDoneTraveled(bookingId);
        var id = new RatingId(bookingId, userId);
        var e = ratingMapper.toEntity(ratingRequest);
        e.setId(id);
        var booking =
                bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not " +
                        "found"));
        if (booking.getStatus() != EnumBookingStatus.BookingStatus.SUCCESS)
            throw new EntityNotFoundException("Booking is not success to action rating!.");
        e.setBooking(booking);
        e.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
        e.setCreateDate(new Date());
        ratingRepository.save(e);
    }

    @Override
    public void update(Long bookingId, Long userId, RatingRequest ratingRequest) {
//        isOutOfDateToRating(bookingId);
//        isDoneTraveled(bookingId);
        var booking =
                bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not " +
                        "found"));
        if (booking.getStatus() != EnumBookingStatus.BookingStatus.SUCCESS)
            throw new EntityNotFoundException("Booking is not success to action rating!.");
        var id = new RatingId(bookingId, userId);

        var e = ratingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating not found to " +
                "update!."));
        ratingMapper.updateEntityFromDTO(ratingRequest, e);
        e.setUpdateDate(new Date());
        ratingRepository.save(e);
    }

    @Override
    public void deleteRatingById(Long availableTimeId, Long userId) {
        var id = new RatingId(availableTimeId, userId);
        ratingRepository.deleteById(id);
    }

    public void isOutOfDateToRating(Long bookingId) {
        var bool = bookingRepository.isOutDateRating(bookingId, new Date());
        if (!bool) {
            throw new AccessDeniedException("Out of date perform to rating. Rating only can be performed after 7 day of done traveled!.");
        }
    }

    public void isDoneTraveled(Long bookingId) {
        var bool = bookingRepository.isDoneTraveled(bookingId, new Date());
        if (!bool) {
            throw new AccessDeniedException("User is not done Traveled at this property to action rating.");
        }
    }
}
