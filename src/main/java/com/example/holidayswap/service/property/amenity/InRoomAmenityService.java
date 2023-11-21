package com.example.holidayswap.service.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface InRoomAmenityService {
    Page<InRoomAmenityResponse> gets(String name, Pageable pageable);

    Page<InRoomAmenityResponse> gets(Long inRoomAmenityTypeId, Pageable pageable);

    List<InRoomAmenityResponse> gets(Long propertyId);

    List<InRoomAmenityResponse> getByInRoomAmenityType(Long InRoomAmenityTypeId);

    List<InRoomAmenityResponse> gets();

    List<InRoomAmenityResponse> gets(Long propertyId, Long inRoomAmenityTypeId);


    InRoomAmenityResponse get(Long id);

    InRoomAmenityResponse create(InRoomAmenityRequest dtoRequest, MultipartFile inRoomAmenityIcon);

    InRoomAmenityResponse update(Long id, InRoomAmenityRequest dtoRequest, MultipartFile inRoomAmenityIcon);

    void delete(Long id);

}
