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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final InRoomAmenityTypeServiceImpl inRoomAmenityTypeService;
    private final PropertyImageServiceImpl propertyImageService;
    private final CoOwnerRepository coOwnerRepository;
    private final AvailableTimeService availableTimeService;

    @Override
    public Page<RoomResponse> gets(Date checkIn, Date checkOut, double min, double max, Set<Long> listOfInRoomAmenity, Set<Long> listOfPropertyView, Set<Long> listOfPropertyType, Pageable pageable) {
        var dto = coOwnerRepository.findHavingAvailableTime(checkIn, checkOut, min, max, listOfInRoomAmenity, listOfPropertyView, listOfPropertyType, pageable);

        var response = dto.map(e -> {
            var mapperE = RoomMapper.INSTANCE.toDtoResponse(e);
            var avalabletimes = availableTimeService.getAllByCoOwnerId(e.getCoOwner().getId());
            mapperE.setAvailableTimes(avalabletimes);
            return mapperE;
        });
        response.forEach(e -> {
            var inRoomAmenityTypeResponses = inRoomAmenityTypeService.gets(e.getProperty().getId());
            var propertyImages = propertyImageService.gets(e.getProperty().getId());
            e.getProperty().setPropertyImage(propertyImages);
            e.getProperty().setInRoomAmenityType(inRoomAmenityTypeResponses);
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
