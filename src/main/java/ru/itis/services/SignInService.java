package ru.itis.services;

import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.models.User;

import java.util.Optional;

public interface SignInService {

    User signIn(SignInDto signInDto);

    Optional<User> signIn(String cookie);
}
