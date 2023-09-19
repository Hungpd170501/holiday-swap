package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.PropertyContractRequest;
import com.example.holidayswap.domain.dto.response.property.PropertyContractResponse;
import com.example.holidayswap.domain.entity.property.PropertyContract;
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

    @Override
    public List<PropertyContractResponse> gets(Long propertyId) {
        var propertyContract = propertyContractRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId);
        var propertyContractResponse = propertyContract.stream().map(PropertyContractMapper.INSTANCE::toDtoResponse).toList();
        return propertyContractResponse;
    }

    @Override
    public PropertyContractResponse get(Long id) {
        var propetyContract = propertyContractRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException(CONTRACT_NOT_FOUND));
        var propetyContractRespones = PropertyContractMapper.INSTANCE.toDtoResponse(propetyContract);
        return propetyContractRespones;
    }

    @Override
    public PropertyContract create(Long propertyId, PropertyContractRequest propertyContractRequest) {
        var propertyContracts = propertyContractRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId);
        if (propertyContracts.size() >= 1) throw new EntityNotFoundException(CONTRACT_NOT_FOUND);
        var propertyContract = PropertyContractMapper.INSTANCE.toEntity(propertyContractRequest);
        propertyContract.setPropertyId(propertyId);
        var propertyContractNew = propertyContractRepository.save(propertyContract);
        return propertyContractNew;
    }

    @Override
    public PropertyContract update(Long id, PropertyContractRequest propertyContractRequest) {
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
