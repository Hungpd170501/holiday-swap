package com.example.holidayswap.service.property.ownership;

import com.example.holidayswap.domain.dto.request.property.ownership.ContractImageRequest;
import com.example.holidayswap.domain.dto.request.property.ownership.OwnershipRequest;
import com.example.holidayswap.domain.dto.response.property.ownership.ContractImageResponse;
import com.example.holidayswap.domain.dto.response.property.ownership.OwnershipResponse;
import com.example.holidayswap.domain.entity.property.ownership.ContractImage;
import com.example.holidayswap.domain.entity.property.ownership.ContractStatus;
import com.example.holidayswap.domain.entity.property.ownership.Ownership;
import com.example.holidayswap.domain.entity.property.ownership.OwnershipId;
import com.example.holidayswap.domain.entity.property.vacation.Vacation;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.property.ownership.ContractImageMapper;
import com.example.holidayswap.domain.mapper.property.ownership.OwnershipMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.repository.property.ownership.OwnerShipRepository;
import com.example.holidayswap.repository.property.vacation.VacationRepository;
import com.example.holidayswap.service.property.vacation.VacationService;
import com.example.holidayswap.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OwnerShipServiceImpl implements OwnerShipService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final OwnerShipRepository ownerShipRepository;
    private final ContractImageService contractImageService;

    private final VacationRepository vacationRepository;

    @Autowired
    private final VacationService vacationService;

//    @Override
//    public List<OwnershipResponse> getListByPropertyId(Long propertyId) {
//        var dtoResponse = ownerShipRepository.findAllByPropertyIdAndIsDeletedIsFalse(propertyId).
//                stream().map(OwnershipMapper.INSTANCE::toDtoResponse).toList();
//        return dtoResponse;
//    }

//    @Override
//    public List<OwnershipResponse> getListByUserId(Long userId) {
//        var dtoResponse = ownerShipRepository.findAllByUserIdAndIsDeletedIsFalse(userId).
//                stream().map(OwnershipMapper.INSTANCE::toDtoResponse).toList();
//        return dtoResponse;
//    }

//    @Override
//    public OwnershipResponse get(Long propertyId, Long userId) {
//        var dtoResponse = OwnershipMapper.INSTANCE.toDtoResponse(
//                ownerShipRepository.findAllByPropertyIdAndUserIdAndIsDeleteIsFalse(propertyId, userId).
//                        orElseThrow(() -> new EntityNotFoundException(OWNERSHIP_NOT_FOUND)));
//        dtoResponse.setContractImages(contractImageService.gets(propertyId, userId));
//        return dtoResponse;
//    }

    @Override
    public OwnershipResponse create12312(Long propertyId,
                                    Long userId,
                                    OwnershipRequest dtoRequest) {
//        get(propertyId, userId);
        var entity = OwnershipMapper.INSTANCE.toEntity(dtoRequest);
        var property = propertyRepository.findPropertyById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND));
        var id = new OwnershipId(propertyId, userId, dtoRequest.getRoomId());

        entity.setId(id);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(ContractStatus.PENDING);
        ownerShipRepository.save(entity);
        dtoRequest.getVacations().forEach(e -> {
            vacationService.create(entity, e);
        });
        return OwnershipMapper.INSTANCE.toDtoResponse(entity);
    }

    @Override
    @Transactional
    public Ownership create(Long propertyId,
                                    Long userId,
                                    OwnershipRequest dtoRequest,
                                    List<MultipartFile> contractImages) {
//
        var flag = false;
        //check validation vacation of this apartment already exist in database ?
        if(dtoRequest.getVacations().size() >1 ){
            flag = Helper.checkOverlapTime(dtoRequest.getVacations());
        }else {
            flag = true;
        }

        if(!flag) throw new EntityNotFoundException("This time coincides with the previously created time period ");

//        // get id user is login
//        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        User user = (User) principal;
//        var checkListOwnerShip = ownerShipRepository.findAllByPropertyIdAndIsDeletedIsFalseAndStatusAndRoomId(propertyId, ContractStatus.ACCEPTED, dtoRequest.getRoomId());
//        if (checkListOwnerShip.size() > 0) {
//            checkListOwnerShip.stream().filter(e -> e.getStartTime())
//        }
        var entity = OwnershipMapper.INSTANCE.toEntity(dtoRequest);
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        entity.setStartTime(dateFormat.format(dtoRequest.getStartTime()));
        entity.setEndTime(dateFormat.format(dtoRequest.getEndTime()));
        var property = propertyRepository.findPropertyById(propertyId).orElseThrow(
                () -> new EntityNotFoundException(PROPERTY_NOT_FOUND));
        var user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(USER_NOT_FOUND));
        var id = new OwnershipId(propertyId, userId, dtoRequest.getRoomId());

        //check validation vacation
        Ownership checkOwnerShipAlreadyExist = ownerShipRepository.findByIdAndStatus(id, ContractStatus.ACCEPTED);
        if (checkOwnerShipAlreadyExist != null) {
            throw new EntityNotFoundException("This apartment you already created");
        }
        if(dtoRequest.getStartTime().after(dtoRequest.getEndTime())) throw new EntityNotFoundException("Start year must be before end year");

        entity.setId(id);
        entity.setProperty(property);
        entity.setUser(user);
        entity.setStatus(ContractStatus.PENDING);
        ownerShipRepository.save(entity);
        Collection<ContractImage> contractImages1 = new ArrayList<>();
        //save contract images
        contractImages.forEach(e -> {
            ContractImageRequest c = new ContractImageRequest();
            c.setPropertyId(propertyId);
            c.setUserId(userId);
            c.setRoomId(dtoRequest.getRoomId());
            contractImages1.add(contractImageService.createAndReturnObject(c, e));
        });
        entity.setContractImages(contractImages1);
        ownerShipRepository.save(entity);

//        var dtoResponse = OwnershipMapper.INSTANCE.toDtoResponse(entity);

        dtoRequest.getVacations().forEach(e -> {
            vacationService.create(entity, e);
        });
        return entity;
    }

    @Override
    public OwnershipResponse update(Long propertyId, Long userId) {
        return null;
    }

    @Override
    public void delete(Long propertyId, Long userId) {

    }
}
