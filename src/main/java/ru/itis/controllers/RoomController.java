package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.RoomInfoDto;
import ru.itis.dto.RoomNamesDto;
import ru.itis.dto.UserProfileDto;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.RoomService;
import ru.itis.services.UserService;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getRoomCreationPage() {
        return "create_room_page";
    }

    @GetMapping("/{roomName:.+}")
    public ModelAndView getRoom(@PathVariable String roomName,
                                Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        userDetails.setUser(userService.getUserById(user.getId()));
        UserProfileDto dto = UserProfileDto.from(userDetails.getUser());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("room_page");
        modelAndView.addObject("user", dto);
        modelAndView.addObject("room", roomService.getRoomByGeneratedName(roomName));
        return modelAndView;
    }

    @PostMapping
    public String createNewRoom(@RequestParam String name, Authentication authentication) {
        User creator = ((UserDetailsImpl)authentication.getPrincipal()).getUser();
        RoomInfoDto infoDto = RoomInfoDto.builder()
                .name(name)
                .build();
        infoDto.setOwner(creator);
        roomService.createRoom(infoDto);
        return "redirect:/profile";
    }
}
