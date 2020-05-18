package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.FileInfo;
import ru.itis.models.Homework;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeworkDescriptionDto {

    private String text;

    private UploadedFileInfoDto attachment;

    private String sendURL;

    public static HomeworkDescriptionDto from(Homework homework) {
        HomeworkDescriptionDto description =  HomeworkDescriptionDto.builder()
                .text(homework.getText())
                .sendURL(homework.getForGroup().getIdentifier())
                .build();
        if (homework.getAttachment() != null) {
            FileInfo attachment = homework.getAttachment();
            description.setAttachment(UploadedFileInfoDto.builder()
                    .originalFileName(attachment.getOriginalFileName())
                    .url(attachment.getUrl())
                    .build());
        }
        return description;
    }

}
