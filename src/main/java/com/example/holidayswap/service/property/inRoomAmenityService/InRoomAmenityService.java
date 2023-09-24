//package com.example.holidayswap.service.property.inRoomAmenityService;
//
//import com.example.holidayswap.domain.dto.request.property.inRoomAmenity.InRoomAmenityRequest;
//import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.InRoomAmenityResponse;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//
//public interface InRoomAmenityService {
//    Page<InRoomAmenityResponse> gets(String searchName, Long idInRoomAmenityType, Pageable pageable);
//
//    List<InRoomAmenityResponse> gets(Long propertyId);
//
//    List<InRoomAmenityResponse> gets(Long propertyId, Long inRoomAmenityTypeId);
//
//    List<InRoomAmenityResponse> getsByInRoomAmenityTypeId(Long inRoomAmenityTypeId);
//
//    InRoomAmenityResponse get(Long id);
//
//    InRoomAmenityResponse create(InRoomAmenityRequest inRoomAmenityRequest);
//
//    InRoomAmenityResponse update(Long id, InRoomAmenityRequest inRoomAmenityRequest);
//
//    void delete(Long id);
//
//}
