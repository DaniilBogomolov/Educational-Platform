package ru.itis.controllers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.ChatService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/messages")
@Slf4j
public class MessageController {

    @Autowired
    private ChatService chatService;

    //    private Set<User> activeUsers = new HashSet<>();
    private List<User> activeUsers = new ArrayList<>();


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageDto>> getMessagesHistoryForRoom(@RequestParam("roomGeneratedName") String roomGeneratedName,
                                                                      Authentication authentication) {
        User newUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
//        activeUsers.add(newUser);
        if (!activeUsers.contains(newUser)) activeUsers.add(newUser);
        List<MessageDto> messagesFromHistory = chatService.getAllNonExpiredMessagesForRoom(roomGeneratedName, LocalDateTime.now());
        return ResponseEntity.ok(messagesFromHistory);
    }

    @SneakyThrows
    @GetMapping(value = "/getUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MessageDto>> getNewMessage(@RequestParam("roomGeneratedName") String roomGeneratedName,
                                                          Authentication authentication) {
        LocalDateTime time = LocalDateTime.now();
        User newUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        int index = activeUsers.indexOf(newUser);
        synchronized (activeUsers.get(index)) {
            activeUsers.get(index).wait();
        }
//        for (User user : activeUsers) {
//            synchronized (user) {
//                if (user.equals(newUser)) {
//                    user.wait();
//                }
//            }
//        }
        List<MessageDto> newMessages = chatService.getAllNonExpiredMessagesSentAfter(roomGeneratedName, time);
        return ResponseEntity.ok(newMessages);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> receiveMessageFromUser(@RequestBody MessageDto messageDto) {
        LocalDateTime timeReceived = LocalDateTime.now();
        chatService.saveNewMessage(messageDto, timeReceived);
        for (int i = 0; i < activeUsers.size(); i++) {
            synchronized (activeUsers.get(i)) {
                User user = activeUsers.get(i);
                if (user.getRooms().stream().map(Room::getIdentifier).anyMatch(generatedName -> generatedName.equals(messageDto.getRoomGeneratedName()))) {
                    activeUsers.get(i).notify();
                }
            }
        }
//        for (User user : activeUsers) {
//            synchronized (user) {
//                if (user.getRooms().stream().map(Room::getIdentifier).anyMatch(generatedName -> generatedName.equals(messageDto.getRoomGeneratedName()))) {
//                    user.notify();
//                }
//            }
//        }
        return ResponseEntity.ok().build();
    }
}
