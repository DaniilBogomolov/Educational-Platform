package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.HomeworkAssignmentDto;
import ru.itis.dto.HomeworkDescriptionDto;
import ru.itis.models.Homework;
import ru.itis.models.User;
import ru.itis.repositories.HomeworkRepository;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.HomeworkService;

import javax.transaction.Transactional;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @GetMapping("/{roomName}")
    public String getHomeworkPassPage(@PathVariable String roomName,
                                      Authentication authentication,
                                      ModelMap modelMap) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        modelMap.addAttribute("info", homeworkService.getInfo(roomName, userDetails.getUser().getLogin()));
        return "homework_page";
    }

    @GetMapping("/user/login/{login}")
    public ResponseEntity<List<HomeworkDescriptionDto>> getHomeworksForUser(@PathVariable String login) {
        return ResponseEntity.ok(homeworkService.findAllByUserLogin(login));
    }

    @GetMapping("/room/{roomName}")
    public String getHomeworksForRoom(@PathVariable String roomName) {

    }



    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createHomework(@RequestBody HomeworkAssignmentDto homeworkAssignment) {
        homeworkService.createHomework(homeworkAssignment);
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendHomework(@RequestBody HomeworkAssignmentDto homework) {
        homeworkService.accept(homework);
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }
}
