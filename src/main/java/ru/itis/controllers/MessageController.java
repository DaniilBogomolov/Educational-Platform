package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.MessageDto;
import ru.itis.dto.RoomMessageDto;
import ru.itis.services.ChatService;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@Controller
public class MessageController {

    @Autowired
    private ChatService chatService;


    @MessageMapping("/room/{roomName}")
    @SendTo("/topic/room/{roomName}")
    public MessageDto sendMessage(@DestinationVariable String roomName,
                                  MessageDto messageDto) {
        return chatService.saveNewMessage(messageDto, now());
    }

    @GetMapping("/messages/{roomName}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String roomName) {
        return ResponseEntity.ok(chatService.getAllNonExpiredMessagesForRoom(roomName, now()));
    }
}
