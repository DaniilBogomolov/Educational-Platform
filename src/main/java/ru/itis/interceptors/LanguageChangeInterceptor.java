package ru.itis.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageChangeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getParameter("lang") != null) {
            String lang = request.getParameter("lang");
            response.addCookie(new Cookie("locale", lang));
            return true;
        }
        return true;
    }
}
