package ru.itis.services;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.models.FileInfo;

import java.util.List;

public interface FileService {

    FileInfo saveFile(MultipartFile multipartFile, String cookieValue);

    List<FileInfo> findFilesByUserCookie(String cookieValue);

    Resource loadFileAsResource(String filename);

    MediaType getFileType(String fileName);
}
