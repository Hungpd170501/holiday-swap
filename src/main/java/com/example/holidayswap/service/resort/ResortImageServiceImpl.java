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
        var entities = resortImageRepository.findAllByResortId(resortId);

        var dtoResponse = entities.stream().map(ResortImageMapper.INSTANCE::toDtoResponse).toList();
        return dtoResponse;
    }

    @Override
    public ResortImageResponse get(Long id) {
        var entity = resortImageRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_IMAMGE_NOT_FOUND));
        var dtoRespones = ResortImageMapper.INSTANCE.toDtoResponse(entity);
        return dtoRespones;
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
        var entity = ResortImageMapper.INSTANCE.toEntity(dtoRequest);
        var created = resortImageRepository.save(entity);
        var dtoReponse = ResortImageMapper.INSTANCE.toDtoResponse(created);
        return dtoReponse;
    }

    @Override
    public ResortImageResponse update(Long id, MultipartFile multipartFile) {
        var entity = resortImageRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_IMAMGE_NOT_FOUND));
        String link;
        try {
            link = fileService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        entity.setLink(link);
        var updated = resortImageRepository.save(entity);
        var dtoReponse = ResortImageMapper.INSTANCE.toDtoResponse(updated);
        return dtoReponse;
    }

    @Override
    public void delete(Long id) {
        var entity = (resortImageRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(RESORT_IMAMGE_NOT_FOUND)));
        entity.setDeleted(true);
        resortImageRepository.save(entity);
    }
}
