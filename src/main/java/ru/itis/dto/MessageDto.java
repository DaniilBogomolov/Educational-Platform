package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.FileInfo;
import ru.itis.models.Message;
import ru.itis.models.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String text;

    private String senderFullName;

    private String roomGeneratedName;

    private String login;

    private UploadedFileInfoDto attachment;

    public static MessageDto from(Message message) {
        User sender = message.getSender();
        return MessageDto.builder()
                .text(message.getText())
                .senderFullName(sender.getFirstName() + " " + sender.getLastName())
                .roomGeneratedName(message.getSentFrom().getIdentifier())
                .login(message.getSender().getLogin())
                .build();
    }

    public static MessageDto from(Message message, FileInfo info) {
        MessageDto messageDto = from(message);
        if (info != null) {
            messageDto.setAttachment(UploadedFileInfoDto.builder()
                    .originalFileName(info.getOriginalFileName())
                    .url(info.getUrl())
                    .build());
        }
        return messageDto;
    }
}
