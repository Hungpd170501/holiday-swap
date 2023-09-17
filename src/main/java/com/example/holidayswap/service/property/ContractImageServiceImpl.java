package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.domain.entity.property.ContractImage;
import com.example.holidayswap.repository.property.ContractImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractImageServiceImpl implements ContractImageService {
    private final ContractImageRepository contractImageRepository;
    private final FileService fileService;

    @Override
    public List<ContractImageResponse> gets(Long idProperty) {
        return null;
    }

    @Override
    public ContractImageResponse get(Long id) {
        return null;
    }

    @Override
    public ContractImage create(Long contractId, MultipartFile multipartFile) {
        String link = null;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContractImage propertyImage = new ContractImage();
        propertyImage.setPropertyContractId(contractId);
        propertyImage.setLink(link);
        var contractImageNew = contractImageRepository.save(propertyImage);
        return contractImageNew;
    }

    @Override
    public ContractImage update(Long Id, Long contractId, ContractImageRequest contractImageRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
