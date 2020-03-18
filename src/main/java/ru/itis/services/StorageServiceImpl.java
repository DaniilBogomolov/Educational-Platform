package ru.itis.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

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
