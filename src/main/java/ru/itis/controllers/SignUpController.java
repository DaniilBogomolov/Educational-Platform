package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;

import javax.validation.Valid;

@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @Value("${error.alreadyExistError}")
    private String alreadyExistError;

    @GetMapping
    public String getSignUpForm(Model model) {
        model.addAttribute("signUpDto", new SignUpDto());
        return "sign_up_page";
    }

    @PostMapping
    public String signUp(@Valid SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign_up_page";
        } else {
            boolean isSignedUp = signUpService.signUp(signUpDto);
            if (isSignedUp) {
                return "redirect:/home";
            } else {
                return "redirect:/signUp";
            }
        }
    }
}
