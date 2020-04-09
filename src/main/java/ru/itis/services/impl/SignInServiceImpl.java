package ru.itis.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.models.Cookie;
import ru.itis.models.User;
import ru.itis.repositories.UserCookieRepository;
import ru.itis.repositories.UserRepository;
import ru.itis.services.SignInService;

import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    @Qualifier("userRepositoryJpaImpl")
    private UserRepository userRepository;

    @Autowired
    private UserCookieRepository cookieRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${secret}")
    private String secret;


    @Override
    public User signIn(SignInDto signInDto) {
        System.out.println(signInDto);
        Optional<User> userCandidate = userRepository.findUserByLogin(signInDto.getLogin());
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (encoder.matches(signInDto.getPassword(), user.getPassword())) {
                return user;
            } throw new AccessDeniedException("Wrong email/password");
        } throw new AccessDeniedException("No user found");

    }


    @Override
    public Optional<User> signIn(String cookie) {
        Optional<Cookie> cookieCandidate = cookieRepository.findCookieByValue(cookie);
        if (cookieCandidate.isPresent()) {
            return Optional.ofNullable(cookieCandidate.get().getUser());
        } else {
            return Optional.empty();
        }
    }
}
