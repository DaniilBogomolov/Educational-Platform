package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.services.JwtService;
import ru.itis.services.SignInService;

@RestController
@RequestMapping("/api/signIn")
public class SignInRestController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public TokenDto signIn(SignInDto signInDto) {
        return jwtService.generateToken(signInService.signIn(signInDto));
    }
}
