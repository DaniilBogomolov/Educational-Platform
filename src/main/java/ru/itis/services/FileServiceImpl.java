package ru.itis.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileRepository;
import ru.itis.repositories.UserCookieRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserCookieRepository userCookieRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public FileInfo saveFile(MultipartFile multipartFile, String userCookie) {
        FileInfo fileInfo = createFileInfoFromMultipartFile(multipartFile, userCookie);
        fileRepository.save(fileInfo);
        storageService.saveFileToStorage(multipartFile, fileInfo.getStorageFileName());
        return fileInfo;
    }

    public List<FileInfo> findFilesByUserCookie(String userCookie) {
        Long userID = userCookieRepository.findCookieByValue(userCookie).get().getUser().getId();
        return fileRepository.getFilesByUploader(userID);
    }

    private FileInfo createFileInfoFromMultipartFile(MultipartFile multipartFile, String userCookie) {
        String newFileName = UUID.randomUUID().toString();
        return FileInfo.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .storageFileName(newFileName)
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .url("files/" + newFileName)
                .owner(userCookieRepository.findCookieByValue(userCookie).get().getUser())
                .build();
    }

    @SneakyThrows
    @Override
    public Resource loadFileAsResource(String filename) {
        File fileFromSystem = storageService.findFileByGeneratedFileName(filename);
        return new FileSystemResource(fileFromSystem);
    }

    @Override
    public MediaType getFileType(String fileName) {
        Optional<FileInfo> fileInfo = fileRepository.getFileByOriginalFileName(fileName);
        String type = null;
        if (fileInfo.isPresent()) {
            type = fileInfo.get().getType();
        }
        return type == null ? MediaType.ALL : MediaType.valueOf(type);
    }
}
