package com.cornello.prototype.service.impl;

import com.cornello.prototype.entity.FileEntity;
import com.cornello.prototype.repository.FileRepository;
import com.cornello.prototype.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileRepository fileRepository;

    @Override
    public FileEntity store(MultipartFile file) throws IOException {
        String fileName = "";
        String orginalFileName = file.getOriginalFilename();
        if(!Objects.isNull(orginalFileName)) {
            fileName = StringUtils.cleanPath(orginalFileName);
        }
        FileEntity fileEntity = new FileEntity(fileName, file.getContentType(), file.getBytes());

        return fileRepository.save(fileEntity);
    }

    @Override
    public FileEntity getFile(String id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public Stream<FileEntity> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}