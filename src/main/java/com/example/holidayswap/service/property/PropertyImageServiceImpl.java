package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyImageRequest;
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
import static com.example.holidayswap.constants.ErrorMessage.PROPERTY_IMAMGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyImageServiceImpl implements PropertyImageService {
    private final PropertyImageRepository propertyImageRepository;
    private final FileService fileService;

    @Override
    public List<PropertyImageResponse> gets(Long propertyId) {
        return propertyImageRepository.findAllByPropertyId(propertyId).stream().
                map(PropertyImageMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public PropertyImageResponse get(Long id) {
        return PropertyImageMapper.INSTANCE.toDtoResponse(propertyImageRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_IMAMGE_NOT_FOUND)));
    }

    @Override
    public PropertyImageResponse create(Long propertyId, MultipartFile multipartFile) {
        String link;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var dtoRequest = new PropertyImageRequest();
        dtoRequest.setPropertyId(propertyId);
        dtoRequest.setLink(link);
        return PropertyImageMapper.INSTANCE.toDtoResponse(propertyImageRepository.
                save(PropertyImageMapper.INSTANCE.toEntity(dtoRequest)));
    }

    @Override
    public PropertyImageResponse update(Long id, MultipartFile multipartFile) {
        var entity = propertyImageRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_IMAMGE_NOT_FOUND));
        delete(id);
        var dtoResponse = create(entity.getPropertyId(), multipartFile);
        return dtoResponse;
    }

    @Override
    public void delete(Long id) {
        PropertyImage propertyImage = propertyImageRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(PROPERTY_IMAGE_NOT_FOUND));
        propertyImage.setDeleted(true);
        propertyImageRepository.save(propertyImage);
    }
}
