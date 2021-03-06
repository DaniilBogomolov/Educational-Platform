package ru.itis.services;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.dto.UploadedFileInfoDto;
import ru.itis.models.FileInfo;

import java.util.List;

public interface FileService {

    FileInfo saveFile(MultipartFile multipartFile, String cookieValue);

    FileInfo saveFile(MultipartFile multipartFile, Long userID);

    List<FileInfo> findFilesByUserCookie(String cookieValue);

    Resource loadFileAsResource(String filename);

    MediaType getFileType(String fileName);

    List<UploadedFileInfoDto> findFilesByUserID(Long id);

    List<UploadedFileInfoDto> findFilesByUserLogin(String login);

    FileInfo getFileByFullURL(String fullURL);
}
