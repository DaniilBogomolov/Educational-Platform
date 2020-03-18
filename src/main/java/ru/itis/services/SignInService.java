package ru.itis.services;

import ru.itis.models.User;

import java.util.Optional;

public interface SignInService {
    String signIn(String login, String password);

    Optional<User> signIn(String cookie);
}
