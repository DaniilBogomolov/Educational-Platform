package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.services.FileService;

@Controller
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FileService fileService;


    @PostMapping
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                   @CookieValue(name = "AuthCookie") String userCookie) {
        if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().isEmpty()) {
            fileService.saveFile(multipartFile, userCookie);
        }
        return getFiles(userCookie);
    }


    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource file = fileService.loadFileAsResource(fileName);
        MediaType fileType = fileService.getFileType(fileName);
        return ResponseEntity.ok()
                .contentType(fileType)
                .body(file);
    }

    @GetMapping
    public ModelAndView getFiles(@CookieValue(name = "AuthCookie", required = false) String userCookie) {
        ModelAndView modelAndView = new ModelAndView();
        if (userCookie != null) {
            modelAndView.addObject("userCookie", userCookie);
            modelAndView.addObject("files", fileService.findFilesByUserCookie(userCookie));
        }
        modelAndView.setViewName("upload_files_page");
        return modelAndView;
    }


}
