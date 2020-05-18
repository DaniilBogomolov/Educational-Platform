package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeworkPageInfo {

    private String roomIdentifier;

    private String senderLogin;

    private List<UploadedFileInfoDto> files;
}
