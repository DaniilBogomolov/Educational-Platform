package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UserProfileDto;
import ru.itis.models.User;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHomePage(Authentication auth) {
        if (auth == null) {
            return new ModelAndView("home_page");
        } else {
            User user = ((UserDetailsImpl) auth.getPrincipal()).getUser();
            UserProfileDto dto = UserProfileDto.from(userService.getUserById(user.getId()));
            return new ModelAndView("home_page", "user", dto);
        }
    }
}
