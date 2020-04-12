package ru.itis.services;

import ru.itis.dto.MessageDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

    List<MessageDto> getAllNonExpiredMessages();

    void saveNewMessage(MessageDto messageDto, LocalDateTime time);

    List<MessageDto> getAllNonExpiredMessagesForRoom(String roomGeneratedName, LocalDateTime now);

    List<MessageDto> getAllNonExpiredMessagesSentAfter(String roomGeneratedName, LocalDateTime time);
}
