package com.cornello.prototype.service;

import java.io.IOException;
import java.util.stream.Stream;

import com.cornello.prototype.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
      FileEntity store(MultipartFile file) throws IOException;
      FileEntity getFile(String id);
     Stream<FileEntity> getAllFiles();
}