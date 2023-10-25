package com.example.holidayswap.service.address;

import com.example.holidayswap.domain.dto.request.address.LocationRequest;
import com.example.holidayswap.domain.dto.response.address.LocationResponse;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.address.CountryMapper;
import com.example.holidayswap.domain.mapper.address.DistrictMapper;
import com.example.holidayswap.domain.mapper.address.LocationMapper;
import com.example.holidayswap.domain.mapper.address.StateOrProvinceMapper;
import com.example.holidayswap.repository.address.CountryRepository;
import com.example.holidayswap.repository.address.DistrictRepository;
import com.example.holidayswap.repository.address.StateOrProvinceRepository;
import com.example.holidayswap.repository.resort.ResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final DistrictRepository districtRepository;
    private final StateOrProvinceRepository stateOrProvinceRepository;
    private final CountryRepository countryRepository;
    private final ResortRepository resortRepository;

    @Override
    public void updateLocation(Long resortId, LocationRequest locationRequest) {
        var resort = resortRepository.findByIdAndDeletedFalse(resortId)
                .orElseThrow(() -> new EntityNotFoundException("Resort not found"));
        LocationMapper.INSTANCE.updateLocation(resort, LocationMapper.INSTANCE.toResort(locationRequest));
        districtRepository.findByCodeEquals(locationRequest.getDistrict().getCode())
                .ifPresentOrElse(
                        resort::setDistrict,
                        () -> resort.setDistrict(DistrictMapper.INSTANCE.toDistrict(locationRequest.getDistrict())));
        stateOrProvinceRepository.findByCodeEquals(locationRequest.getStateOrProvince().getCode())
                .ifPresentOrElse(
                        resort.getDistrict()::setStateProvince,
                        () -> resort.getDistrict()
                                .setStateProvince(
                                        StateOrProvinceMapper.INSTANCE.toStateOrProvince(locationRequest.getStateOrProvince())));
        countryRepository.findByCodeEquals(locationRequest.getCountry().getCode())
                .ifPresentOrElse(
                        resort.getDistrict().getStateProvince()::setCountry,
                        () -> resort.getDistrict().getStateProvince()
                                .setCountry(CountryMapper.INSTANCE.toCountry(locationRequest.getCountry()))
                );
        resortRepository.save(resort);
    }


    @Override
    public LocationResponse getLocation(Long resortId) {
        return resortRepository.findById(resortId)
                .map(LocationMapper.INSTANCE::toLocationResponse)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));
    }

    @Override
    public List<LocationResponse> getLocationList(List<Long> ids) {
        return resortRepository.findAllById(ids)
                .stream()
                .map(LocationMapper.INSTANCE::toLocationResponse)
                .toList();
    }
}
