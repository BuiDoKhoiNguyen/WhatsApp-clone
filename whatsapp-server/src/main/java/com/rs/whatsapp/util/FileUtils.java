package com.rs.whatsapp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {
    private FileUtils() {}

    public static byte[] readFileFromLocation(String fileUrl) {
        if(fileUrl == null || fileUrl.isBlank()) {
            return new byte[0];
        }
        try {
            Path file = new File(fileUrl).toPath();
            return Files.readAllBytes(file);
        } catch (IOException e) {
            log.warn("Failed to read file from location: {}", fileUrl);
        }

        return new byte[0];
    }
}
