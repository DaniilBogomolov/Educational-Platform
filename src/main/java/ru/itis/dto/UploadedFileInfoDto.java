package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.FileInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadedFileInfoDto {

    private String url;

    private String originalFileName;

    public static UploadedFileInfoDto from(FileInfo fileInfo) {
        return UploadedFileInfoDto.builder()
                .originalFileName(fileInfo.getOriginalFileName())
                .url(fileInfo.getUrl())
                .build();
    }
}
