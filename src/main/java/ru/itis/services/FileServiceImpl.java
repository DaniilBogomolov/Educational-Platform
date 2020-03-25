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
import ru.itis.models.User;
import ru.itis.repositories.FileRepository;
import ru.itis.repositories.UserCookieRepository;
import ru.itis.repositories.UserRepository;

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
    private UserRepository userRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public FileInfo saveFile(MultipartFile multipartFile, String userCookie) {
        return saveFile(multipartFile,
                userCookieRepository.findCookieByValue(userCookie)
                        .get()
                        .getUser()
                        .getId());
    }

    @Override
    public FileInfo saveFile(MultipartFile multipartFile, Long userID) {
        FileInfo fileInfo = createFileInfoFromMultipartFile(multipartFile,
                userRepository.find(userID).get());
        fileRepository.save(fileInfo);
        storageService.saveFileToStorage(multipartFile, fileInfo.getStorageFileName());
        return fileInfo;
    }

    public List<FileInfo> findFilesByUserCookie(String userCookie) {
        Long userID = userCookieRepository.findCookieByValue(userCookie).get().getUser().getId();
        return findFilesByUserID(userID);
    }

    private FileInfo createFileInfoFromMultipartFile(MultipartFile multipartFile, User user) {
        String newFileName = UUID.randomUUID().toString();
        return FileInfo.builder()
                .originalFileName(multipartFile.getOriginalFilename())
                .storageFileName(newFileName)
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .url("files/" + newFileName)
                .owner(user)
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

    @Override
    public List<FileInfo> findFilesByUserID(Long id) {
        return fileRepository.getFilesByUploader(id);
    }
}
