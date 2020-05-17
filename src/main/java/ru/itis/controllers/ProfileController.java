package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UserProfileDto;
import ru.itis.models.User;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.FileService;
import ru.itis.services.RoomService;
import ru.itis.services.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Value("${domain}")
    private String domain;

    @GetMapping
    public String getMyProfile(Authentication authentication,
                             ModelMap model) {
        //Need to update user, if they confirmed account or created room
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        userDetails.setUser(userService.getUserById(user.getId()));
        UserProfileDto dto = UserProfileDto.from(userDetails.getUser());
        model.addAttribute("user", dto);
        return "profile_page";
    }

    @GetMapping("/{userLogin}")
    public String getUserProfile(Authentication authentication,
                                 Model model,
                                 @PathVariable String userLogin) {
        User accessor = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        model.addAttribute("user", UserProfileDto.from(accessor, userService.getUserByLogin(userLogin)));
        return "profile_page";
    }

    @PostMapping
    public String uploadProfilePhoto(@RequestParam("file") MultipartFile file,
                                           Authentication authentication) {

        User currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String newPhotoURI = fileService.saveFile(file, currentUser.getId()).getUrl();
        currentUser.setProfilePhotoLink(domain.concat(newPhotoURI));
        userService.saveUserProfilePhotoInDB(currentUser);

        return "redirect:/profile";
    }
}
