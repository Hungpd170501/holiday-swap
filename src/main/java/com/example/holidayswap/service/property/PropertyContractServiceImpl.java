package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyContractRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyContractResponse;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.inRoomAmenity.PropertyContractMapper;
import com.example.holidayswap.repository.property.PropertyContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.CONTRACT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PropertyContractServiceImpl implements PropertyContractService {
    private final PropertyContractRepository propertyContractRepository;

    private final ContractImageService contractImageService;

    @Override
    public List<PropertyContractResponse> gets(Long propertyId) {
        var propertyContract = propertyContractRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId);
        return propertyContract.stream().map(element -> {
            var dtoResponse = PropertyContractMapper.INSTANCE.toDtoResponse(element);
            dtoResponse.setContractImages(contractImageService.gets(element.getId()));
            return dtoResponse;
        }).toList();
    }

    @Override
    public PropertyContractResponse get(Long id) {
        var propetyContract = propertyContractRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(CONTRACT_NOT_FOUND));
        return PropertyContractMapper.INSTANCE.toDtoResponse(propetyContract);
    }

    @Override
    public PropertyContractResponse create(Long propertyId, PropertyContractRequest propertyContractRequest) {
        var propertyContracts = propertyContractRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId);
        if (!propertyContracts.isEmpty()) throw new EntityNotFoundException(CONTRACT_NOT_FOUND);
        var propertyContract = PropertyContractMapper.INSTANCE.toEntity(propertyContractRequest);
        propertyContract.setPropertyId(propertyId);
        var propertyContractNew = propertyContractRepository.save(propertyContract);
        return PropertyContractMapper.INSTANCE.toDtoResponse(propertyContractNew);
    }

    @Override
    public PropertyContractResponse update(Long id, PropertyContractRequest propertyContractRequest) {
        var propertyContract = propertyContractRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(CONTRACT_NOT_FOUND));
        propertyContract.setDeleted(true);
        propertyContractRepository.save(propertyContract);
        var propertyContractUpdate = create(propertyContract.getPropertyId(), propertyContractRequest);
        return propertyContractUpdate;
    }

    @Override
    public void delete(Long id) {
        var propertyContract = propertyContractRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(CONTRACT_NOT_FOUND));
        propertyContract.setDeleted(true);
        propertyContractRepository.save(propertyContract);
    }
}
