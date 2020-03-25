package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.FileService;

@Controller
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                   Authentication authentication) {
        if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().isEmpty()) {
            fileService.saveFile(multipartFile, ((UserDetailsImpl) authentication.getPrincipal()).getUser().getId());
        }
        return getFiles(authentication);
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
    public ModelAndView getFiles(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload_files_page");
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        modelAndView.addObject("files", fileService.findFilesByUserID(user.getId()));
        return modelAndView;
    }

}
