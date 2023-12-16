package com.example.holidayswap.services.impl;

import com.example.holidayswap.domain.dto.request.resort.amenity.ResortAmenityRequest;
import com.example.holidayswap.domain.dto.response.resort.amenity.ResortAmenityResponse;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenity;
import com.example.holidayswap.domain.entity.resort.amentity.ResortAmenityType;
import com.example.holidayswap.domain.exception.DataIntegrityViolationException;
import com.example.holidayswap.domain.exception.DuplicateRecordException;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.amenity.ResortAmenityMapper;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityRepository;
import com.example.holidayswap.repository.resort.amenity.ResortAmenityTypeRepository;
import com.example.holidayswap.service.FileService;
import com.example.holidayswap.service.resort.amenity.ResortAmenityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResortAmenityServiceTest {
    ResortAmenityServiceImpl resortAmenityService;
    ResortAmenityRepository resortAmenityRepository;
    ResortAmenityTypeRepository resortAmenityTypeRepository;
    FileService fileService;
    ResortAmenityMapper resortAmenityMapper;

    @BeforeEach
    void beforeEach() {
        resortAmenityRepository = mock(ResortAmenityRepository.class);
        resortAmenityMapper = mock(ResortAmenityMapper.class);
        resortAmenityTypeRepository = mock(ResortAmenityTypeRepository.class);
        fileService = mock(FileService.class);
        resortAmenityService = new ResortAmenityServiceImpl(resortAmenityRepository, resortAmenityTypeRepository, fileService);
    }

    @Test
    void gets_ShouldReturnListResortAmenityResponse_WhenInputIsNameAndPageable() {
        var resortAmenity1 = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenity2 = ResortAmenity.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        Page<ResortAmenity> entities = new PageImpl<>(List.of(resortAmenity1, resortAmenity2));
        when(resortAmenityRepository.findAllByResortAmenityName("name", Pageable.ofSize(1))).thenReturn(entities);

        var resortAmenityResponse1 = ResortAmenityResponse.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenityResponse2 = ResortAmenityResponse.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        Page<ResortAmenityResponse> expectedDtoResponses = new PageImpl<>(List.of(resortAmenityResponse1, resortAmenityResponse2));

        Page<ResortAmenityResponse> actualDtoResponses = resortAmenityService.gets("name", Pageable.ofSize(1));

        assertEquals(expectedDtoResponses, actualDtoResponses);
    }

    @Test
    void gets_ShouldReturnListResortAmenityResponse_WhenInputIsamenityTypeIdAndPageable(){
        var resortAmenity1 = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenity2 = ResortAmenity.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        Page<ResortAmenity> entities = new PageImpl<>(List.of(resortAmenity1, resortAmenity2));
        when(resortAmenityRepository.findAllByResortAmenityTypeId(1L, Pageable.ofSize(1))).thenReturn(entities);

        var resortAmenityResponse1 = ResortAmenityResponse.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenityResponse2 = ResortAmenityResponse.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        Page<ResortAmenityResponse> expectedDtoResponses = new PageImpl<>(List.of(resortAmenityResponse1, resortAmenityResponse2));

        Page<ResortAmenityResponse> actualDtoResponses = resortAmenityService.gets(1L, Pageable.ofSize(1));

        assertEquals(expectedDtoResponses, actualDtoResponses);
    }

    @Test
    void gets_ShouldReturnListResortAmenityResponse_WhenInputIsamenityTypeId(){
        var resortAmenity1 = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenity2 = ResortAmenity.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        List<ResortAmenity> entities = new ArrayList<>(List.of(resortAmenity1, resortAmenity2));
        when(resortAmenityRepository.findAllByResortAmenityTypeId(1L)).thenReturn(entities);

        var resortAmenityResponse1 = ResortAmenityResponse.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenityResponse2 = ResortAmenityResponse.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        List<ResortAmenityResponse> expectedDtoResponses = new ArrayList<>(List.of(resortAmenityResponse1, resortAmenityResponse2));

        List<ResortAmenityResponse> actualDtoResponses = resortAmenityService.gets(1L);

        assertEquals(expectedDtoResponses, actualDtoResponses);
    }
    @Test
    void gets_ShouldReturnListResortAmenityResponse_WhenInputIsamenityTypeIdAndResortId(){
        var resortAmenity1 = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenity2 = ResortAmenity.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        List<ResortAmenity> entities = new ArrayList<>(List.of(resortAmenity1, resortAmenity2));
        when(resortAmenityRepository.findAllByResortAmenityTypeIdAndResortId(1L,1L)).thenReturn(entities);

        var resortAmenityResponse1 = ResortAmenityResponse.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        var resortAmenityResponse2 = ResortAmenityResponse.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        List<ResortAmenityResponse> expectedDtoResponses = new ArrayList<>(List.of(resortAmenityResponse1, resortAmenityResponse2));

        List<ResortAmenityResponse> actualDtoResponses = resortAmenityService.gets(1L,1L);

        assertEquals(expectedDtoResponses, actualDtoResponses);
    }

    @Test
    void get_ShouldReturnErrorResortAmenityNotFound_WhenInputIsAmenityTypeId(){
        EntityNotFoundException exception =assertThrows(EntityNotFoundException.class,()->{
            resortAmenityService.get(1L);
        });
        assertEquals("Resort amenity not found.",exception.getMessage());
    }

    @Test
    void get_ShouldReturnResortAmenityResponse_WhenInputIsAmenityTypeId(){
        var resortAmenity = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        when(resortAmenityRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(java.util.Optional.of(resortAmenity));

        var resortAmenityResponse = ResortAmenityResponse.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();

        ResortAmenityResponse actualDtoResponse = resortAmenityService.get(1L);

        assertEquals(resortAmenityResponse, actualDtoResponse);
    }

    @Test
    void create_ShouldReturnError_WhenDuplicateAmenity(){
        ResortAmenityRequest resortAmenityRequest = ResortAmenityRequest.builder()
                .resortAmenityName("name")
                .build();
        when(resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse("name")).thenReturn(java.util.Optional.of(ResortAmenity.builder().build()));
        DuplicateRecordException exception =assertThrows(DuplicateRecordException.class,()->{
            resortAmenityService.create(resortAmenityRequest,null);
        });
        assertEquals("Duplicate resort amenity.",exception.getMessage());
    }

    @Test
    void create_ShouldReturnError_WhenResortAmenityTypeDeleted(){
        ResortAmenityRequest resortAmenityRequest = ResortAmenityRequest.builder()
                .resortAmenityName("name")
                .build();
        when(resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse("name")).thenReturn(Optional.empty());
        when(resortAmenityTypeRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty());
        DataIntegrityViolationException exception =assertThrows(DataIntegrityViolationException.class,()->{
            resortAmenityService.create(resortAmenityRequest,null);
        });
        assertEquals("Resort type amenity has deleted.",exception.getMessage());
    }

//    @Test
//    void create_ShouldReturnResortAmenityResponse_WhenInputIsResortAmenityRequest() throws IOException {
//        ResortAmenityRequest resortAmenityRequest = ResortAmenityRequest.builder()
//                .resortAmenityName("name")
//                .resortAmenityTypeId(1L)
//                .build();
//        ResortAmenityType resortAmenityType = ResortAmenityType.builder().id(1L).build();
//        ResortAmenityResponse resortAmenityResponse = ResortAmenityResponse.builder().resortAmenityTypeId(1L).resortAmenityLinkIcon("Link").build();
//        ResortAmenity resortAmenity = ResortAmenity.builder().resortAmenityTypeId(1L).build();
//
//        when(resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse("name")).thenReturn(Optional.empty());
//        when(resortAmenityTypeRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.ofNullable(resortAmenityType));
//        when(fileService.uploadFile(null)).thenReturn("link");
//        resortAmenity.setResortAmenityLinkIcon("link");
//        when(resortAmenityRepository.save(resortAmenity)).thenReturn(resortAmenity);
//        ResortAmenityResponse actualDtoResponse = resortAmenityService.create(resortAmenityRequest,null);
//        assertEquals(resortAmenityResponse.getResortAmenityTypeId(),actualDtoResponse.getResortAmenityTypeId());
//    }

    @Test
    void update_ShouldReturnError_WhenEntityNotFound(){
        EntityNotFoundException exception =assertThrows(EntityNotFoundException.class,()->{
            resortAmenityService.update(1L,ResortAmenityRequest.builder().build(),null);
        });
        assertEquals("Resort amenity not found.",exception.getMessage());
    }

    @Test
    void update_ShouldReturnError_WhenDuplicateAmenity(){
        ResortAmenityRequest resortAmenityRequest = ResortAmenityRequest.builder()
                .resortAmenityName("name")
                .build();
        ResortAmenity resortAmenityFound = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        ResortAmenity resortAmenity = ResortAmenity.builder()
                .id(2L)
                .resortAmenityName("name")
                .build();
        when(resortAmenityRepository.findByIdAndIsDeletedFalse(2L)).thenReturn(Optional.ofNullable(resortAmenity));
        when(resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse("name")).thenReturn(Optional.ofNullable(resortAmenityFound));
        DuplicateRecordException exception =assertThrows(DuplicateRecordException.class,()->{
            resortAmenityService.update(2L,resortAmenityRequest,null);
        });
        assertEquals("Duplicate resort amenity.",exception.getMessage());
    }

    @Test
    void update_ShouldReturnError_WhenAmenityTypeIsDeleted(){
        ResortAmenity resortAmenityFound = ResortAmenity.builder()
                .id(1L)
                .resortAmenityName("name")
                .build();
        when(resortAmenityRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.ofNullable(resortAmenityFound));
        when(resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse("name")).thenReturn(Optional.empty());
        when(resortAmenityTypeRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty());
        DataIntegrityViolationException exception =assertThrows(DataIntegrityViolationException.class,()->{
            resortAmenityService.update(1L,ResortAmenityRequest.builder().build(),null);
        });
        assertEquals("Resort type amenity has deleted.",exception.getMessage());

    }

    @Test
    void update_ShouldReturnResortAmenityResponse_WhenInputIsResortAmenityRequestAndLinkIsNull() throws IOException {
        ResortAmenityRequest resortAmenityRequest = ResortAmenityRequest.builder()
                .resortAmenityName("name")
                .resortAmenityTypeId(1L)
                .build();
        ResortAmenityType resortAmenityType = ResortAmenityType.builder().id(1L).build();
        ResortAmenityResponse resortAmenityResponse = ResortAmenityResponse.builder().resortAmenityTypeId(1L).resortAmenityLinkIcon("Link").build();
        ResortAmenity resortAmenity = ResortAmenity.builder().resortAmenityTypeId(1L).build();

        when(resortAmenityRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.ofNullable(resortAmenity));
        when(resortAmenityRepository.findByResortAmenityNameEqualsIgnoreCaseAndIsDeletedIsFalse("name")).thenReturn(Optional.empty());
        when(resortAmenityTypeRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.ofNullable(resortAmenityType));

//        when(fileService.uploadFile(null)).thenReturn("link");
//        resortAmenity.setResortAmenityLinkIcon("link");
        when(resortAmenityRepository.save(resortAmenity)).thenReturn(resortAmenity);
        ResortAmenityResponse actualDtoResponse = resortAmenityService.update(1L, resortAmenityRequest, null);
        assertEquals(resortAmenityResponse.getResortAmenityTypeId(), actualDtoResponse.getResortAmenityTypeId());
    }

}
