package ru.itis.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public String signIn(String login, String password) {
//        Optional<User> userCandidate = userRepository.findUserByLogin(login);
//        if (userCandidate.isPresent()) {
//            User user = userCandidate.get();
//            if (encoder.matches(password, user.getPassword())) {
//                String cookieValue = UUID.randomUUID().toString();
//                Cookie cookie = Cookie.builder()
//                        .value(cookieValue)
//                        .user(user)
//                        .build();
//                try {
//                    cookieRepository.save(cookie);
//                } catch (DuplicateKeyException e) {
//                    cookieRepository.update(cookie);
//                }
//                return cookieValue;
//            }
//        }
//        return null;

        Optional<User> userCandidate = userRepository.findUserByLogin(login);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (encoder.matches(password, user.getPassword())) {
                return Jwts.builder()
                        .claim("id", user.getId())
                        .claim("role", user.getRole().name())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
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
