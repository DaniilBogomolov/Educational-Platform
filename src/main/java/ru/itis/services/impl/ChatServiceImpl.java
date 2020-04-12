package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.RoomRepository;
import ru.itis.repositories.UserRepository;
import ru.itis.services.ChatService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    @Qualifier("userRepositoryJpaImpl")
    private UserRepository userRepository;

    @Override
    public List<MessageDto> getAllNonExpiredMessages() {
        return messageRepository.findAll().stream()
                .filter(message -> LocalDateTime.now().compareTo(message.getExpiringTime()) > 0)
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveNewMessage(MessageDto messageDto, LocalDateTime time) {
        Message message = Message.builder()
                .text(messageDto.getText())
                .timeSent(time)
                .expiringTime(time.plusMonths(1))
                .sender(userRepository.findUserByLogin(messageDto.getLogin()))
                .sentFrom(roomRepository.findByGeneratedName(messageDto.getRoomGeneratedName()))
                .build();
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public List<MessageDto> getAllNonExpiredMessagesForRoom(String roomGeneratedName, LocalDateTime now) {
        return messageRepository.getAllMessagesForRoom(roomRepository.findByGeneratedName(roomGeneratedName).getRoomId())
                .stream()
                .filter(message -> message.getExpiringTime().compareTo(now) > 0)
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MessageDto> getAllNonExpiredMessagesSentAfter(String roomGeneratedName, LocalDateTime time) {
        return messageRepository.getAllMessagesForRoom(roomRepository.findByGeneratedName(roomGeneratedName).getRoomId())
                .stream()
                .filter(message -> message.getExpiringTime().compareTo(time) > 0)
                .filter(message -> message.getTimeSent().compareTo(time) > 0)
                .map(MessageDto::from)
                .collect(Collectors.toList());
    }


}
