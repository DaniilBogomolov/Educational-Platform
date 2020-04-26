package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.itis.dto.MessageDto;
import ru.itis.services.ChatService;

import java.time.LocalDateTime;

@Controller
public class MessageController {

    @Autowired
    private ChatService chatService;


    @MessageMapping("/room/{roomName}")
    @SendTo("/topic/room/{roomName}")
    public MessageDto sendMessage(@DestinationVariable String roomName,
                                  MessageDto messageDto) {
        return chatService.saveNewMessage(messageDto, LocalDateTime.now());
    }
}
