package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyImageRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import com.example.holidayswap.domain.mapper.property.PropertyImageMapper;
import com.example.holidayswap.repository.property.PropertyImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyImageServiceImpl implements PropertyImageService {
    private final PropertyImageRepository propertyImageRepository;

    private final FileService fileService;

    private final PropertyImageMapper propertyImageMapper;

    @Override
    public List<PropertyImageResponse> gets(Long idProperty) {
        List<PropertyImage> propertyImages = propertyImageRepository.findAllByPropertyId(idProperty);
        List<PropertyImageResponse> propertyImageResponses = propertyImages.stream().map(propertyImageMapper::toPropertyImageResponse).toList();
        return propertyImageResponses;
    }

    @Override
    public PropertyImageResponse get(Long id) {
        PropertyImage propertyImage = propertyImageRepository.findById(id).orElseThrow();
        PropertyImageResponse propertyImageResponse = propertyImageMapper.toPropertyImageResponse(propertyImage);
        return propertyImageResponse;
    }

    @Override
    public PropertyImage create(Long idProperty, MultipartFile propertyImageFile) {
        String link = null;
        try {
            link = fileService.uploadFile(propertyImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setPropertyId(idProperty);
        propertyImage.setLink(link);
        var propertyImageNew = propertyImageRepository.save(propertyImage);
        return propertyImageNew;
    }

    @Override
    public PropertyImage update(Long Id, Long idProperty, PropertyImageRequest propertyImageRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {
        PropertyImage propertyImage = propertyImageRepository.findById(id).orElseThrow();
        propertyImage.setDeleted(true);
        propertyImageRepository.save(propertyImage);
    }
}
