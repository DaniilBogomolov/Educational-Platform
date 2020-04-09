package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @Value("${error.alreadyExistError}")
    private String alreadyExistError;

    @GetMapping
    public String getSignUpForm() {
        return "sign_up_page";
    }

    @PostMapping
    public ModelAndView signUp(SignUpDto signUpDto) {
        ModelAndView modelAndView = new ModelAndView();
        boolean isSignedUp = signUpService.signUp(signUpDto);
        if (isSignedUp) {
            modelAndView.setViewName("home_page");
        } else {
            modelAndView.addObject("error", alreadyExistError);
            modelAndView.setViewName("sign_up_page");
        }
        return modelAndView;
    }
}
