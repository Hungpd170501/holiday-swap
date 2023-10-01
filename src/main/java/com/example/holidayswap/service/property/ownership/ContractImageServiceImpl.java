package com.example.holidayswap.service.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ownership.ContractImage;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ownership.ContractImageMapper;
import com.example.holidayswap.repository.property.ownership.ContractImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.CONTRACT_IMAGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContractImageServiceImpl implements ContractImageService {
    private final ContractImageRepository contractImageRepository;
    private final FileService fileService;

    @Override
    public List<ContractImageResponse> gets(Long propertyId, Long userId) {
        var dtoResponses = contractImageRepository.findAllByPropertyIdAndUserIdAndIsDeletedIsFalse(propertyId, userId);
        return dtoResponses.stream().map(ContractImageMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public ContractImageResponse get(Long id) {
        var dtoResponse = contractImageRepository.findByIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        return ContractImageMapper.INSTANCE.toDtoResponse(dtoResponse);
    }

    @Override
    public ContractImageResponse create(ContractImageRequest dtoRequest, MultipartFile multipartFile) {
        String link = null;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContractImage contractImage = ContractImageMapper.INSTANCE.toEntity(dtoRequest);
        contractImage.setLink(link);
        return ContractImageMapper.INSTANCE.toDtoResponse(contractImageRepository.save(contractImage));
    }

    @Override
    public ContractImageResponse update(Long id, MultipartFile multipartFile) {
        var entity = contractImageRepository.findByIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        delete(id);
        ContractImageRequest dtoRequest = new ContractImageRequest();
        dtoRequest.setPropertyId(entity.getPropertyId());
        dtoRequest.setUserId(entity.getUserId());
        return create(dtoRequest, multipartFile);
    }

    @Override
    public void delete(Long id) {
        var contractImage = contractImageRepository.findByIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        contractImage.setIsDeleted(true);
        contractImageRepository.save(contractImage);
    }
    @Transactional
    @Override
    public ContractImage createAndReturnObject(ContractImageRequest dtoRequest, MultipartFile multipartFile) {
        String link = null;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContractImage contractImage = ContractImageMapper.INSTANCE.toEntity(dtoRequest);
        contractImage.setLink(link);
        return contractImageRepository.save(contractImage);
    }
}
