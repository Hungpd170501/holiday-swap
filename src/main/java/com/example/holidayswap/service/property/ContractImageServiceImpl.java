package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ContractImage;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ContractImageMapper;
import com.example.holidayswap.repository.property.ContractImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public List<ContractImageResponse> gets(Long contractId) {
        var contractImages = contractImageRepository.findContractImagesByPropertyContractIdAndIsDeletedIsFalse(contractId);
        var contractImageResponse = contractImages.stream().map(ContractImageMapper.INSTANCE::toDtoResponse).toList();
        return contractImageResponse;
    }

    @Override
    public ContractImageResponse get(Long id) {
        var contractImage = contractImageRepository.findContractImageByIdAndIsDeleteIsFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        var contractImageResponse = ContractImageMapper.INSTANCE.toDtoResponse(contractImage);
        return contractImageResponse;
    }

    @Override
    public ContractImage create(Long contractId, MultipartFile multipartFile) {
        String link = null;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContractImage contractImage = new ContractImage();
        contractImage.setPropertyContractId(contractId);
        contractImage.setLink(link);
        var contractImageNew = contractImageRepository.save(contractImage);
        return contractImageNew;
    }

    @Override
    public ContractImage update(Long id, MultipartFile multipartFile) {
        var contractImage = contractImageRepository.findContractImageByIdAndIsDeleteIsFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        contractImage.setDeleted(true);
        var contractImageNew = create(contractImage.getPropertyContractId(), multipartFile);
        return contractImageNew;
    }

    @Override
    public void delete(Long id) {
        var contractImage = contractImageRepository.findContractImageByIdAndIsDeleteIsFalse(id).
                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
        contractImage.setDeleted(true);
        contractImageRepository.save(contractImage);
    }
}
