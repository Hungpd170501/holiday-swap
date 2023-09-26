package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.dto.request.property.ContractImageRequest;
import com.example.holidayswap.domain.dto.response.property.inRoomAmenity.ContractImageResponse;
import com.example.holidayswap.domain.entity.auth.User;
import com.example.holidayswap.domain.entity.property.*;
import com.example.holidayswap.domain.mapper.property.ContractImageMapper;
import com.example.holidayswap.repository.auth.UserRepository;
import com.example.holidayswap.repository.property.OwnerShipRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service

public class OwnerShipServiceImpl implements IOwnerShipService {

    @Autowired
    private ContractImageService contractImageService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private OwnerShipRepository ownerShipRepository;

    @Override
    public Ownership create(Long propertyId, Date startDate, Date endDate, List<MultipartFile> contractImages) {

        // get id user is login
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = (User) principal;
        Ownership ownership1 = new Ownership();
        //set id is composite key
        ownership1.setId(new OwnershipId(propertyId,4L));
//        ownerShipRepository.save(ownership1);

        Collection<ContractImage> imageContractResponses = new ArrayList<>();
        for (MultipartFile contractImage : contractImages) {
            imageContractResponses.add(contractImageService.create(ownership1.getId(),contractImage));
        }
        ownership1.setContractImages(imageContractResponses);
        ownership1.setProperty(propertyRepository.findPropertyById(6L).get());
        ownership1.setUser(userRepository.findById(4L).get());
        ownership1.setStartTime(startDate);
        ownership1.setEndTime(endDate);
        ownership1.setStatus(PropertyContractStatus.PENDING);
        ownership1.setType(PropertyContractType.RIGHT_TO_USE);

        ownerShipRepository.save(ownership1);

        return ownership1;
    }
}
