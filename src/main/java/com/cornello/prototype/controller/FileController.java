package com.cornello.prototype.controller;

import com.cornello.prototype.entity.FileEntity;
import com.cornello.prototype.model.ResponseFile;
import com.cornello.prototype.model.ResponseMessage;
import com.cornello.prototype.service.FileStorageService;
import com.cornello.prototype.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/v1")
//@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        log.info("Uploading filename:{}",file.getOriginalFilename());
        try {
            storageService.store(file);
            log.info("Uploaded the file successfully: {}", file.getOriginalFilename());
            FileEntity fileEntity = storageService.store(file);

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseFile(
                    fileEntity.getId(),
                    fileEntity.getName(),
                    Utility.getDownloadUri(fileEntity),
                    fileEntity.getType(),
                    fileEntity.getData().length));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(fileEntity ->
             new ResponseFile(
                    fileEntity.getId(),
                    fileEntity.getName(),
                    Utility.getDownloadUri(fileEntity),
                    fileEntity.getType(),
                    fileEntity.getData().length)
        ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getFile(@PathVariable String id) {
        FileEntity fileEntity = storageService.getFile(id);
        if(null == fileEntity)
            return ResponseEntity.badRequest().body(new ResponseMessage("File not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(fileEntity.getData());
    }
}
