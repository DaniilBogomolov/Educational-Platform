package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.services.ChatService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<Object> receiveMessage() {
        //TODO
        return ResponseEntity.ok().build();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendMessage(@RequestBody MessageDto messageDto) {
        LocalDateTime timeReceived = LocalDateTime.now();
        chatService.saveNewMessage(messageDto, timeReceived);
        return ResponseEntity.ok().build();
    }
}
