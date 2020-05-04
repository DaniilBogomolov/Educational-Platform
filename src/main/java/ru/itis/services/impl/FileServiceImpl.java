package ru.itis.services.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UploadedFileInfoDto;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.repositories.FileRepository;
import ru.itis.repositories.UserCookieRepository;
import ru.itis.repositories.UserRepository;
import ru.itis.services.FileService;
import ru.itis.services.StorageService;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    @Qualifier("fileRepositoryJpaImpl")
    private FileRepository fileRepository;

    @Autowired
    private UserCookieRepository userCookieRepository;

    @Autowired
    @Qualifier("userRepositoryJpaImpl")
    private UserRepository userRepository;

    @Autowired
    private StorageService storageService;

    @Value("${domain.fileURI}")
    private String fileDomainURI;

    @Override
    public FileInfo saveFile(MultipartFile multipartFile, String userCookie) {
        return saveFile(multipartFile,
                userCookieRepository.findCookieByValue(userCookie)
                        .get()
                        .getUser()
                        .getId());
    }

    @Override
    @Transactional
    public FileInfo saveFile(MultipartFile multipartFile, Long userID) {
        FileInfo fileInfo = createFileInfoFromMultipartFile(multipartFile,
                userRepository.find(userID).get());
        fileRepository.save(fileInfo);
        storageService.saveFileToStorage(multipartFile, fileInfo.getStorageFileName());
        return fileInfo;
    }

    public List<FileInfo> findFilesByUserCookie(String userCookie) {
        Long userID = userCookieRepository.findCookieByValue(userCookie).get().getUser().getId();
        return fileRepository.getFilesByUploader(userID);
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
        Optional<FileInfo> fileInfo = fileRepository.getFileByGeneratedFileName(fileName);
        String type = null;
        if (fileInfo.isPresent()) {
            type = fileInfo.get().getType();
        }
        return type == null ? MediaType.ALL : MediaType.valueOf(type);
    }

    @Override
    @Transactional
    public List<UploadedFileInfoDto> findFilesByUserID(Long id) {
        return fileRepository.getFilesByUploader(id).stream()
                .peek(fileInfo -> fileInfo.setUrl(fileDomainURI.concat(fileInfo.getStorageFileName())))
                .map(UploadedFileInfoDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UploadedFileInfoDto> findFilesByUserLogin(String login) {
        return fileRepository.getFilesByUploaderLogin(login).stream()
                .peek(fileInfo -> fileInfo.setUrl(fileDomainURI.concat(fileInfo.getStorageFileName())))
                .map(UploadedFileInfoDto::from)
                .collect(Collectors.toList());
    }
}
