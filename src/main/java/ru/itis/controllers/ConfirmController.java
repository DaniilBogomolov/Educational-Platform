package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.services.ConfirmService;

@Controller
public class ConfirmController {

    @Autowired
    private ConfirmService confirmService;

    @Value("${error.wrongIdentifierError}")
    private String wrongIdentifierError;

    @RequestMapping(value = "/confirm/{confirm-code}", method = RequestMethod.GET)
    public ModelAndView confirm(@PathVariable("confirm-code") String confirmIdentifier) {
        ModelAndView modelAndView = new ModelAndView();
        if (!confirmService.confirm(confirmIdentifier)) {
            modelAndView.addObject("error", wrongIdentifierError);
        }
        modelAndView.setViewName("confirm_page");
        return modelAndView;
    }
}
