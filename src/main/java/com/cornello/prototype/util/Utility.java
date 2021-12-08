package com.cornello.prototype.util;

import com.cornello.prototype.entity.FileEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Utility {

    private Utility() {
        //empty
    }

    public static String getDownloadUri(FileEntity fileEntity) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/files/")
                .path(fileEntity.getId())
                .toUriString();
    }
}
