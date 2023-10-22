package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.RoomDTO;
import com.example.holidayswap.domain.dto.response.property.RoomResponse;
import com.example.holidayswap.domain.mapper.property.RoomMapper;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.service.property.amenity.InRoomAmenityTypeServiceImpl;
import com.example.holidayswap.service.property.timeFame.AvailableTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final InRoomAmenityTypeServiceImpl inRoomAmenityTypeService;
    private final PropertyImageServiceImpl propertyImageService;
    private final CoOwnerRepository coOwnerRepository;
    private final AvailableTimeService availableTimeService;

    @Override
    public Page<RoomResponse> gets(Date checkIn, Date checkOut, double min, double max, Pageable pageable) {
        var dto = coOwnerRepository.findHavingAvailableTime(checkIn, checkOut, min, max, pageable);

        var response = dto.map(RoomMapper.INSTANCE::toDtoResponse);
        response.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getProperty().getId());
            var propertyImages = propertyImageService.gets(e.getProperty().getId());
            e.getProperty().setPropertyImage(propertyImages);
            e.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
            var avalabletimes = availableTimeService.getAllByCoOwnerId(e.getCoOwner().getId());
            e.setAvailableTimes(avalabletimes);
        });
        return response;
    }

    @Override
    public RoomDTO get(String roomId) {
//        var response = timeFrameRepository.findRoomByRoomId(
//                roomId).orElseThrow(() -> new EntityNotFoundException("Room is not found or No time is public."));
//        response.setAvailableTimes(availableTimeRepository.findAllByRoomId(roomId));
//        return response;
        return null;
    }
}
