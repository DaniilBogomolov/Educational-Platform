package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.models.User;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.ChatService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private ChatService chatService;

    private List<User> activeUsers;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageDto>> getMessagesHistoryForRoom(@RequestParam("roomGeneratedName") String roomGeneratedName,
                                                                      Authentication authentication) {
        User newUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (activeUsers == null) activeUsers = new ArrayList<>();
        activeUsers.add(newUser);
        List<MessageDto> messagesFromHistory = chatService.getAllNonExpiredMessagesForRoom(roomGeneratedName, LocalDateTime.now());
        return ResponseEntity.ok(messagesFromHistory);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> receiveMessageFromUser(@RequestBody MessageDto messageDto) {
        LocalDateTime timeReceived = LocalDateTime.now();
        chatService.saveNewMessage(messageDto, timeReceived);
        return ResponseEntity.ok().build();
    }
}
