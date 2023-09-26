package com.example.holidayswap.service.resort;

import com.example.holidayswap.domain.dto.request.resort.ResortImageRequest;
import com.example.holidayswap.domain.dto.response.resort.ResortImageResponse;
import com.example.holidayswap.domain.exception.EntityNotFoundException;
import com.example.holidayswap.domain.mapper.resort.ResortImageMapper;
import com.example.holidayswap.repository.resort.ResortImageRepository;
import com.example.holidayswap.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.holidayswap.constants.ErrorMessage.RESORT_IMAMGE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ResortImageServiceImpl implements ResortImageService {
    private final ResortImageRepository resortImageRepository;
    private final FileService fileService;

    @Override
    public List<ResortImageResponse> gets(Long resortId) {
        return resortImageRepository.findAllByResortId(resortId).stream().map(ResortImageMapper.INSTANCE::toDtoResponse).toList();
    }

    @Override
    public ResortImageResponse get(Long id) {
        return ResortImageMapper.INSTANCE.toDtoResponse(resortImageRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_IMAMGE_NOT_FOUND)));
    }

    @Override
    public ResortImageResponse create(Long resortId, MultipartFile multipartFile) {
        String link;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var dtoRequest = new ResortImageRequest();
        dtoRequest.setResortId(resortId);
        dtoRequest.setLink(link);
        return ResortImageMapper.INSTANCE.toDtoResponse(resortImageRepository.save(ResortImageMapper.INSTANCE.toEntity(dtoRequest)));
    }

    @Override
    public ResortImageResponse update(Long id, MultipartFile multipartFile) {
        String link;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var entity = resortImageRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_IMAMGE_NOT_FOUND));
        var dtoRequset = new ResortImageRequest();
        dtoRequset.setLink(link);
        ResortImageMapper.INSTANCE.updateEntityFromDTO(dtoRequset, entity);
        return ResortImageMapper.INSTANCE.toDtoResponse(resortImageRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = (resortImageRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(RESORT_IMAMGE_NOT_FOUND)));
        entity.setDeleted(true);
        resortImageRepository.save(entity);
    }
}
