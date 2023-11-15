package com.example.holidayswap.service.property.rating;

import com.example.holidayswap.domain.dto.request.property.rating.RatingRequest;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
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

import static com.example.holidayswap.constants.ErrorMessage.AVAILABLE_TIME_NOT_FOUND;
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

        StringBuilder convertedFullNameBuilder = new StringBuilder();
        convertedFullNameBuilder.append(userFullName.charAt(0));

        for (int i = 1; i < userFullName.length(); i++) {
            convertedFullNameBuilder.append('*');
        }

        return convertedFullNameBuilder.toString();
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
    public RatingResponse getRatingByPropertyIdAndUserId(Long availableTimeId, Long userId) {
        var e = ratingRepository.findByAvailableTimeIdAndUserId(availableTimeId, userId).orElseThrow(() -> new EntityNotFoundException("User's rating " + "is not found."));
        var rs = ratingMapper.toDtoResponse(e);
        return rs;
    }

    @Override
    public void create(Long availableTimeId, Long userId, RatingRequest ratingRequest) {
        isBooked(availableTimeId, userId);
        isDoneTraveled(availableTimeId, userId);
        var id = new RatingId(availableTimeId, userId);
        var e = ratingMapper.toEntity(ratingRequest);
//        e.setId(id);
        e.setAvailableTime(availableTimeRepository.findById(availableTimeId).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND)));
        e.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
        e.setCreateDate(new Date());
        ratingRepository.save(e);
    }

    @Override
    public void update(Long availableTimeId, Long userId, RatingRequest ratingRequest) {
//        isBooked(availableTimeId, userId);
//        isDoneTraveled(availableTimeId, userId);
//        var id = new RatingId(availableTimeId, userId);
//        var e = ratingMapper.toEntity(ratingRequest);
//        var checkRating = ratingRepository.findByBooking(bookingRepository.findById(ratingRequest.getBookingId()).get());
//        if (checkRating != null) {
//            throw new AccessDeniedException("Already rated");
//        }
////        e.setId(id);
//
//        e.setAvailableTime(availableTimeRepository.findById(availableTimeId).orElseThrow(() -> new EntityNotFoundException(AVAILABLE_TIME_NOT_FOUND)));
//        e.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
//        e.setUpdateDate(new Date());
//        var booking = bookingRepository.findById(ratingRequest.getBookingId()).get();
//        e.setBooking(booking);
//        ratingRepository.save(e);
    }

    @Override
    public void deleteRatingById(Long availableTimeId, Long userId) {
        var id = new RatingId(availableTimeId, userId);
        ratingRepository.deleteById(id);
    }

    public void isBooked(Long availableTimeId, Long userId) {
        var bool = bookingRepository.IsUserBooKed(availableTimeId, userId);
        if (!bool) {
            throw new AccessDeniedException("User is not booked" + " at this property to action rating.");
        }
    }

    public void isDoneTraveled(Long availableTimeId, Long userId) {
        var bool = bookingRepository.isDoneTraveled(availableTimeId, userId, new Date());
        if (!bool) {
            throw new AccessDeniedException("User is not done Traveled" + " at this property to action rating.");
        }
    }
}
