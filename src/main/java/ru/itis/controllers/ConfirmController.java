package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.Mail;
import ru.itis.models.Status;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.ConfirmService;
import ru.itis.services.EmailService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Controller
@RequestMapping("/confirm")
public class ConfirmController {

    @Autowired
    private ConfirmService confirmService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ExecutorService executorService;

    @Value("${error.wrongIdentifierError}")
    private String wrongIdentifierError;

    @GetMapping("/{confirm-code}")
    public ModelAndView confirm(@PathVariable("confirm-code") String confirmIdentifier) {
        ModelAndView modelAndView = new ModelAndView();
        if (!confirmService.confirm(confirmIdentifier)) {
            modelAndView.addObject("error", wrongIdentifierError);
        }
        modelAndView.setViewName("confirm_page");
        return modelAndView;
    }

    @PostMapping
    public String sendNewConfirmMail(Authentication authentication) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (!user.getConfirmed()) {
            Map<String, Object> model = new HashMap<>();
            model.put("identificator", confirmService.getNewConfirmCode(user));
            model.put("firstName", user.getFirstName());
            model.put("lastName", user.getLastName());
            model.put("email", user.getEmail());
            Mail confirmMail = Mail.builder()
                    .subject("Confirm email address")
                    .to(user.getEmail())
                    .build();
            executorService.submit(() -> emailService.sendEmail(confirmMail, "mails/confirmation_mail.ftl", model));
        }
        return "redirect:/profile";
    }
}
