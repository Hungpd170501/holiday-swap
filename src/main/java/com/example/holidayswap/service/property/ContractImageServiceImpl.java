package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ContractImage;
import com.example.holidayswap.domain.entity.property.OwnershipId;
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
//        var contractImages = contractImageRepository.findContractImagesByPropertyContractIdAndIsDeletedIsFalse(contractId);
//        return contractImages.stream().map(ContractImageMapper.INSTANCE::toDtoResponse).toList();
        return null;
    }

    @Override
    public ContractImageResponse get(Long id) {
//        var contractImage = contractImageRepository.findContractImageByIdAndIsDeleteIsFalse(id).
//                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
//        var contractImageResponse = ContractImageMapper.INSTANCE.toDtoResponse(contractImage);
//        return contractImageResponse;
        return null;
    }

    @Override
    public ContractImage create(OwnershipId ownershipId, MultipartFile multipartFile) {
        String link = null;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContractImage contractImage = new ContractImage();
//        contractImage.setPropertyContractId(contractId);
//        contractImage.set
        contractImage.setIsDeleted(false);
        contractImage.setPropertyId(ownershipId.getPropertyId());
        contractImage.setUserId(ownershipId.getUserId());
        contractImage.setLink(link);
        contractImageRepository.save(contractImage);
        return contractImage;
    }

    @Override
    public ContractImageResponse update(Long id, MultipartFile multipartFile) {
//        var contractImage = contractImageRepository.findContractImageByIdAndIsDeleteIsFalse(id).
//                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
//        contractImage.setIsDeleted(true);
//        var contractImageNew = create(contractImage.getPropertyContractId(), multipartFile);
//        return contractImageNew;
        return null;
    }

    @Override
    public void delete(Long id) {
//        var contractImage = contractImageRepository.findContractImageByIdAndIsDeleteIsFalse(id).
//                orElseThrow(() -> new EntityNotFoundException(CONTRACT_IMAGE_NOT_FOUND));
//        contractImage.setIsDeleted(true);
//        contractImageRepository.save(contractImage);
    }
}
