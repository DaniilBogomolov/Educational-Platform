package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeworkAssignmentDto {

    private String text;

    private UploadedFileInfoDto attachment;

    private String roomIdentifier;

    private String senderLogin;
}
