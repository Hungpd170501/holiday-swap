package com.example.holidayswap.service.property;

import com.example.holidayswap.domain.entity.property.PropertyMaintenance;
import com.example.holidayswap.domain.entity.property.PropertyMaintenanceImage;
import com.example.holidayswap.domain.entity.property.PropertyStatus;
import com.example.holidayswap.domain.entity.resort.ResortMaintance;
import com.example.holidayswap.domain.entity.resort.ResortMaintanceImage;
import com.example.holidayswap.domain.entity.resort.ResortStatus;
import com.example.holidayswap.repository.property.PropertyMaintenanceRepository;
import com.example.holidayswap.repository.property.PropertyRepository;
import com.example.holidayswap.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class PropertyMaintenanceServiceImpl implements IPropertyMaintenanceService{
    private final PropertyRepository propertyRepository;
    private final PropertyMaintenanceRepository propertyMaintenanceRepository;
    private final FileService fileService;

    @Override
    public List<String> CreatePropertyMaintance(Long resortId, LocalDateTime startDate, LocalDateTime endDate, PropertyStatus resortStatus, List<MultipartFile> resortImage) {
        var checkResort = propertyRepository.findById(resortId).orElseThrow(() -> new RuntimeException("Property not found"));
        var checkIsDeactivate = propertyMaintenanceRepository.findAllByTypeAndProperty(PropertyStatus.DEACTIVATE,resortId);
        PropertyMaintenance checkIsMaintance = null;
        checkIsMaintance = propertyMaintenanceRepository.findByPropertyIdAndStartDateAndEndDateAndType(resortId, startDate, startDate, PropertyStatus.MAINTENANCE.name());
        if(checkIsDeactivate.size()>0) {
            throw new RuntimeException("Property is deactivated");
        }
        if(resortStatus != PropertyStatus.DEACTIVATE) {
            checkIsMaintance = propertyMaintenanceRepository.findByPropertyIdAndStartDateAndEndDateAndType(resortId, startDate, endDate, PropertyStatus.MAINTENANCE.name());
        }
        if(checkIsMaintance != null) {
            throw new RuntimeException("Property is already in maintenance");
        }
        List<PropertyMaintenanceImage> listimage = new ArrayList<>();
        PropertyMaintenance resortMaintance = new PropertyMaintenance();
        resortMaintance.setStartDate(startDate);
        resortMaintance.setEndDate(endDate);
        resortMaintance.setType(resortStatus);
        resortMaintance.setPropertyId(resortId);
        resortMaintance.setProperty(checkResort);

        var re = propertyMaintenanceRepository.save(resortMaintance);
        resortImage.forEach(image -> {
            try {
                PropertyMaintenanceImage resortMaintanceImage = new PropertyMaintenanceImage();
                resortMaintanceImage.setImageUrl(fileService.uploadFile(image));
                resortMaintanceImage.setPropertyMaintenance(re);
                listimage.add(resortMaintanceImage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        re.setPropertyMaintenanceImages(listimage);
        propertyMaintenanceRepository.save(re);



        return listimage.stream().map(PropertyMaintenanceImage::getImageUrl).toList().stream().toList();
    }

    @Override
    public void DeactivePropertyAtStartDate(LocalDateTime startDate) {
        List<PropertyMaintenance> check = propertyMaintenanceRepository.findByTypeAndStartDate(ResortStatus.DEACTIVATE.name(), startDate);
        if(check.size() == 0) return;
        check.forEach(propertyMaintenance -> {
            propertyMaintenance.getProperty().setStatus(PropertyStatus.DEACTIVATE);
            propertyRepository.save(propertyMaintenance.getProperty());
        });
    }
}
