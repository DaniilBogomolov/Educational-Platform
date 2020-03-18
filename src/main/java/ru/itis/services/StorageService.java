package ru.itis.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

public interface StorageService {

    void saveFileToStorage(MultipartFile file, String generatedFileName);

    File findFileByGeneratedFileName(String filename);
}
