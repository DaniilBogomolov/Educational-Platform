package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.FileInfo;
import ru.itis.models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfoDto {
    private User owner;
    private String fileLink;
    private String filename;

    public static FileInfoDto from(FileInfo fileInfo) {
        return FileInfoDto.builder()
                .owner(fileInfo.getOwner())
                .filename(fileInfo.getOriginalFileName())
                .fileLink(fileInfo.getUrl())
                .build();
    }
}
