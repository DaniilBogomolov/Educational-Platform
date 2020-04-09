package ru.itis.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.dto.TokenDto;
import ru.itis.models.User;
import ru.itis.services.JwtService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${secret}")
    private String secret;

    @Override
    public String parseTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            List<Cookie> authCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("Authorization"))
                    .collect(Collectors.toList());
            if (!authCookie.isEmpty()) {
                return authCookie.get(0).getValue();
            }
        }
        throw new IllegalStateException("Null cookies");
    }

    @Override
    public TokenDto generateToken(User user) {
        String tokenValue = Jwts.builder()
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new TokenDto(tokenValue);
    }

}