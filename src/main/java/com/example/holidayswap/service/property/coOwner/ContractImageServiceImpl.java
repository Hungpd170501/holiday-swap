package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.dto.response.property.coOwner.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.coOwner.ContractImage;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.coOwner.ContractImageMapper;
import com.example.holidayswap.repository.property.coOwner.ContractImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.CONTRACT_IMAGE_NOT_FOUND;
import static com.example.holidayswap.constants.ErrorMessage.TIME_FRAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContractImageServiceImpl implements ContractImageService {
    private final ContractImageRepository contractImageRepository;
    private final FileService fileService;

    @Override
    public List<ContractImageResponse> gets(Long coOwnerId) {
        var dtoResponses = contractImageRepository.findAllByCoOwnerIdAndIsDeletedIsFalse(coOwnerId);
        return dtoResponses.stream().map(ContractImageMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public ContractImageResponse get(Long id) {
        var dtoResponse = contractImageRepository.findByIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        return ContractImageMapper.INSTANCE.toDtoResponse(dtoResponse);
    }

    @Override
    public ContractImageResponse create(Long coOwnerId, MultipartFile multipartFile) {
        String link = null;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContractImage contractImage = new ContractImage();
        contractImage.setLink(link);
        contractImage.setCoOwnerId(coOwnerId);
        return ContractImageMapper.INSTANCE.toDtoResponse(contractImageRepository.save(contractImage));
    }

    @Override
    public void delete(Long id) {
        var contractImage = contractImageRepository.findByIdAndIsDeletedFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        contractImage.setIsDeleted(true);
        contractImageRepository.save(contractImage);
    }

    @Override
    public void appendToCo(Long coOwnerId, ContractImage image) {
        var img = contractImageRepository.findById(image.getId()).orElseThrow(() -> new EntityNotFoundException(TIME_FRAME_NOT_FOUND));
        img.setCoOwnerId(coOwnerId);
        contractImageRepository.save(image);
    }
}
