package com.example.holidayswap.service.property.amenity;

import com.example.holidayswap.domain.dto.request.property.amenity.InRoomAmenityTypeRequest;
import com.example.holidayswap.domain.dto.response.property.amenity.InRoomAmenityTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface InRoomAmenityTypeService {
    Page<InRoomAmenityTypeResponse> gets(String name, Pageable pageable);

    List<InRoomAmenityTypeResponse> gets(Long propertyId);

    List<InRoomAmenityTypeResponse> gets();

    InRoomAmenityTypeResponse get(Long id);

    InRoomAmenityTypeResponse create(InRoomAmenityTypeRequest dtoRequest);

    InRoomAmenityTypeResponse update(Long id, InRoomAmenityTypeRequest dtoRequest);

    void delete(Long id);

}
