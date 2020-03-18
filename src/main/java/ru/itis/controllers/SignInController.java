package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.services.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {

    @Autowired
    private SignInService signInService;

    @Value("${error.wrongCredentialsError}")
    private String wrongCredentialsError;

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView getSignInPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign_in_page");
        return modelAndView;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ModelAndView signIn(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        String cookieValue = signInService.signIn(login, password);
        ModelAndView modelAndView = new ModelAndView();
        if (cookieValue != null) {
            Cookie cookie = new Cookie("AuthCookie", cookieValue);
            response.addCookie(cookie);
            modelAndView.setViewName("home_page");
        } else {
            modelAndView.addObject("error", wrongCredentialsError);
            modelAndView.setViewName("sign_in_page");
        }
        return modelAndView;
    }
}
