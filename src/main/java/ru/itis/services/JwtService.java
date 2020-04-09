package ru.itis.services;

import ru.itis.dto.TokenDto;
import ru.itis.models.User;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {

    String parseTokenFromRequest(HttpServletRequest request);

    TokenDto generateToken(User user);
}
