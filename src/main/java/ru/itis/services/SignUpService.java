package ru.itis.services;

import ru.itis.dto.SignUpDto;

public interface SignUpService {
    boolean signUp(SignUpDto signUpDto);
}
