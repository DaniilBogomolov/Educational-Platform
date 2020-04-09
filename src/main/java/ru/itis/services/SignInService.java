package ru.itis.services;

import ru.itis.dto.TokenDto;
import ru.itis.models.User;

import java.util.Optional;

public interface SignInService {
    String signIn(String login, String password);

//    TokenDto signIn(String lo);

    Optional<User> signIn(String cookie);
}
