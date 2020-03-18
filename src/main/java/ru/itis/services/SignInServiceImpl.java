package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.models.Cookie;
import ru.itis.models.User;
import ru.itis.repositories.UserCookieRepository;
import ru.itis.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCookieRepository cookieRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String signIn(String login, String password) {
        Optional<User> userCandidate = userRepository.findUserByLogin(login);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (encoder.matches(password, user.getPassword())) {
                String cookieValue = UUID.randomUUID().toString();
                Cookie cookie = Cookie.builder()
                        .value(cookieValue)
                        .user(user)
                        .build();
                try {
                    cookieRepository.save(cookie);
                } catch (DuplicateKeyException e) {
                    cookieRepository.update(cookie);
                }
                return cookieValue;
            }
        }
        return null;
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
