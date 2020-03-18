package ru.itis.repositories;

import ru.itis.models.FileInfo;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends CrudRepository<Long, FileInfo> {
    List<FileInfo> getFilesByUploader(Long uploaderId);

    Optional<FileInfo> getFileByOriginalFileName(String fileName);
}
