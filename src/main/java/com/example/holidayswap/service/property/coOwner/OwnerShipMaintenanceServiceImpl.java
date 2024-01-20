package com.example.holidayswap.service.property.coOwner;

import com.example.holidayswap.domain.entity.property.coOwner.CoOwnerMaintenanceStatus;
import com.example.holidayswap.domain.entity.property.coOwner.OwnerShipMaintenance;
import com.example.holidayswap.domain.entity.property.coOwner.OwnerShipMaintenanceImage;
import com.example.holidayswap.repository.property.coOwner.CoOwnerMaintenanceImageRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerMaintenanceRepository;
import com.example.holidayswap.repository.property.coOwner.CoOwnerRepository;
import com.example.holidayswap.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OwnerShipMaintenanceServiceImpl implements IOwnerShipMaintenanceService{

    private final CoOwnerMaintenanceRepository coOwnerMaintenanceRepository;
    private final CoOwnerMaintenanceImageRepository coOwnerMaintenanceImageRepository;
    private final CoOwnerRepository coOwnerRepository;
    private final FileService fileService;
    @Override
    public List<String> CreateOwnerShipMaintenance(Long propertyId, String roomId, LocalDateTime startDate, LocalDateTime endDate, CoOwnerMaintenanceStatus resortStatus, List<MultipartFile> resortImage) {

        var checkIsDeactivate = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentIdAndType(propertyId,roomId,CoOwnerMaintenanceStatus.DEACTIVATE);
        List <OwnerShipMaintenance> checkIsMaintance;
        checkIsMaintance = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentIdAndStartDateAndEndDateAndType(propertyId, startDate, startDate ,roomId,CoOwnerMaintenanceStatus.MAINTENANCE.name().toString());
        if(checkIsDeactivate.size() > 0) {
            throw new RuntimeException("Apartment is deactivated");
        }
        if(resortStatus != CoOwnerMaintenanceStatus.DEACTIVATE) {
            checkIsMaintance = coOwnerMaintenanceRepository.findByPropertyIdAndApartmentIdAndStartDateAndEndDateAndType(propertyId, startDate, endDate ,roomId,   CoOwnerMaintenanceStatus.MAINTENANCE.name().toString());
        }
        if(checkIsMaintance.size() > 0) {
            throw new RuntimeException("Apartment is already in maintenance");
        }
        List<OwnerShipMaintenanceImage> listimage = new ArrayList<>();
        OwnerShipMaintenance resortMaintance = new OwnerShipMaintenance();
        resortMaintance.setStartDate(startDate);
        resortMaintance.setEndDate(endDate);
        resortMaintance.setType(resortStatus);
        resortMaintance.setPropertyId(propertyId);
        resortMaintance.setApartmentId(roomId);

        var re = coOwnerMaintenanceRepository.save(resortMaintance);
        resortImage.forEach(image -> {
            try {
                OwnerShipMaintenanceImage resortMaintanceImage = new OwnerShipMaintenanceImage();
                resortMaintanceImage.setImageUrl(fileService.uploadFile(image));
                resortMaintanceImage.setOwnershipMaintenance(re);
                listimage.add(resortMaintanceImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        re.setOwnershipMaintenanceImages(listimage);
        coOwnerMaintenanceRepository.save(re);

        return listimage.stream().map(OwnerShipMaintenanceImage::getImageUrl).toList().stream().toList();
    }
}
