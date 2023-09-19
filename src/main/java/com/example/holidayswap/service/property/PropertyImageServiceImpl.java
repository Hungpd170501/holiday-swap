package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.PropertyImageResponse;
import com.example.holidayswap.domain.entity.property.PropertyImage;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.PropertyImageMapper;
import com.example.holidayswap.repository.property.PropertyImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_IMAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyImageServiceImpl implements PropertyImageService {
    private final PropertyImageRepository propertyImageRepository;

    private final FileService fileService;

    private final PropertyImageMapper propertyImageMapper;

    @Override
    public List<PropertyImageResponse> gets(Long propertyId) {
        List<PropertyImage> propertyImages = propertyImageRepository.findAllByPropertyIdAndDeletedIsFalse(propertyId);
        List<PropertyImageResponse> propertyImageResponses = propertyImages.stream().map(propertyImageMapper::toDtoResponse).toList();
        return propertyImageResponses;
    }

    @Override
    public PropertyImageResponse get(Long id) {
        PropertyImage propertyImage = propertyImageRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_IMAGE_NOT_FOUND));
        PropertyImageResponse propertyImageResponse = propertyImageMapper.toDtoResponse(propertyImage);
        return propertyImageResponse;
    }

    @Override
    public PropertyImage create(Long propertyId, MultipartFile propertyImageFile) {
        String link = null;
        try {
            link = fileService.uploadFile(propertyImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PropertyImage propertyImage = new PropertyImage();
        propertyImage.setPropertyId(propertyId);
        propertyImage.setLink(link);
        var propertyImageNew = propertyImageRepository.save(propertyImage);
        return propertyImageNew;
    }

    @Override
    public PropertyImage update(Long id, MultipartFile multipartFile) {

        var propertyImage = propertyImageRepository.findByIdAndDeletedIsFalse(id).
                orElseThrow(() -> new EntityNotFoundException(PROPERTY_IMAGE_NOT_FOUND));
        propertyImage.setDeleted(true);
        var contractImageNew = create(propertyImage.getPropertyId(), multipartFile);
        return contractImageNew;
    }

    @Override
    public void delete(Long id) {
        PropertyImage propertyImage = propertyImageRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_IMAGE_NOT_FOUND));
        propertyImage.setDeleted(true);
        propertyImageRepository.save(propertyImage);
    }
}
