package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.User;
import ru.itis.security.http.details.UserDetailsImpl;
import ru.itis.services.FileService;

@Controller
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FileService fileService;


    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
                             Authentication authentication,
                             ModelMap modelMap) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().isEmpty()) {
            fileService.saveFile(multipartFile, user.getId());
        }
        return "redirect:/files";
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
    public String getFiles(Authentication authentication,
                           ModelMap modelMap) {
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        modelMap.addAttribute("files", fileService.findFilesByUserID(user.getId()));
        modelMap.addAttribute("profilePhotoLink", user.getProfilePhotoLink());
        modelMap.addAttribute("firstName", user.getFirstName());
        return "upload_files_page";
    }

}
