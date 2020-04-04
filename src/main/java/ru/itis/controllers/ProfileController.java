package ru.itis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public ModelAndView getProfile() {
        return new ModelAndView("profile_page");
    }


    @PostMapping
    public ModelAndView uploadProfilePhoto(@RequestParam("file") MultipartFile file) {
        return new ModelAndView("profile_page", "profile_image",
                "http://localhost:8080/files/898dfae4-3f50-49d3-a543-b8fdfe727bcb");
    }
}
