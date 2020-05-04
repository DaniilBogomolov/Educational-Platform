package ru.itis.services;

import ru.itis.dto.MessageDto;
import ru.itis.dto.RoomMessageDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

    List<MessageDto> getAllNonExpiredMessages();

    MessageDto saveNewMessage(MessageDto messageDto, LocalDateTime time);

    List<RoomMessageDto> getAllNonExpiredMessagesForRoom(String roomGeneratedName, LocalDateTime now);
}
