package ru.itis.services.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.services.StorageService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.path}")
    private String storagePath;


    @SneakyThrows
    @Override
    public void saveFileToStorage(MultipartFile file, String generatedFileName) {
        Files.copy(file.getInputStream(), Paths.get(storagePath, generatedFileName));
    }

    @SneakyThrows
    @Override
    public File findFileByGeneratedFileName(String filename) {
        return new File(storagePath + "/" + filename);
    }
}
