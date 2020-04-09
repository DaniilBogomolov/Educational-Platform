package ru.itis.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UserProfileDto;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.FileService;
import ru.itis.services.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Value("${domain}")
    private String domain;

    @GetMapping
    public ModelAndView getProfile(Authentication authentication) {
        //Need to update user, if they confirmed account
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUser().getId();
        userDetails.setUser(userService.getUserById(userId));
        UserProfileDto dto = UserProfileDto.from(userDetails.getUser());
        return new ModelAndView("profile_page", "user", dto);
    }


    @PostMapping
    public ModelAndView uploadProfilePhoto(@RequestParam("file") MultipartFile file,
                                           Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();

        User currentUser = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String newPhotoURI = fileService.saveFile(file, currentUser.getId()).getUrl();
        System.out.println(newPhotoURI);
        currentUser.setProfilePhotoLink(domain.concat(newPhotoURI));
        UserProfileDto userProfileDto = userService.saveUserProfilePhotoInDB(currentUser);

        modelAndView.setViewName("profile_page");
        modelAndView.addObject("user", userProfileDto);
        return modelAndView;
    }
}
