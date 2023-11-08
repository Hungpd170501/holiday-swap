package com.example.holidayswap.service.property.rate;

import com.example.holidayswap.domain.dto.request.property.rating.RatingRequest;
import com.example.holidayswap.domain.dto.response.property.rating.RatingResponse;
import com.example.holidayswap.domain.entity.property.rating.RatingId;
import com.example.holidayswap.domain.exception.AccessDeniedException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.rating.RatingMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.booking.BookingRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.rate.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Page<RatingResponse> getListRatingByPropertyId(Long propertyId, Pageable pageable) {
        var entities = ratingRepository.findAllByPropertyId(propertyId, pageable);
        var dto = entities.map(ratingMapper::toDtoResponse);
        return dto;
    }

    @Override
    public Double getRatingOfProperty(Long propertyId) {
        return ratingRepository.calculateRating(propertyId);
    }

    @Override
    public RatingResponse getRatingByPropertyIdAndUserId(Long propertyId, Long userId) {
        var e = ratingRepository.findByPropertyIdAndUserUserIdAnd(propertyId, userId).orElseThrow(() -> new EntityNotFoundException("User's rating " + "is not found."));
        var rs = ratingMapper.toDtoResponse(e);
        return rs;
    }

    @Override
    public void create(Long propertyId, Long userId, RatingRequest ratingRequest) {
        isBooked(propertyId, userId);
        var id = new RatingId(propertyId, userId);
        var e = ratingMapper.toEntity(ratingRequest);
        e.setId(id);
        e.setProperty(propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND)));
        e.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
        ratingRepository.save(e);
    }

    @Override
    public void update(Long propertyId, Long userId, RatingRequest ratingRequest) {
        isBooked(propertyId, userId);
        var id = new RatingId(propertyId, userId);
        var e = ratingMapper.toEntity(ratingRequest);
        e.setId(id);
        e.setProperty(propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException(PROPERTY_NOT_FOUND)));
        e.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)));
        ratingRepository.save(e);
    }

    @Override
    public void deleteRatingById(Long propertyId, Long userId) {
        var id = new RatingId(propertyId, userId);
        ratingRepository.deleteById(id);
    }

    public void isBooked(Long propertyId, Long userId) {
        var bool = bookingRepository.IsUserBooKed(propertyId, userId);
        if (!bool) {
            throw new AccessDeniedException("User is not booked" + " at this property to action rating.");
        }
    }
}
