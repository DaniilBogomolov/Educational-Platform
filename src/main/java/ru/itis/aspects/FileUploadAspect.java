package ru.itis.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.FileInfoDto;
import ru.itis.models.FileInfo;
import ru.itis.models.Mail;
import ru.itis.services.EmailService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Aspect
@Slf4j
@Component
public class FileUploadAspect {

    @Autowired
    private EmailService emailService;


    @Autowired
    private ExecutorService executorService;


    @AfterReturning(value = "execution(* ru.itis.services.FileService.saveFile(..))", returning = "fileInfo")
    public void after(JoinPoint joinPoint, FileInfo fileInfo) {
        FileInfoDto fileInfoDto = FileInfoDto.from(fileInfo);
        Map<String, Object> model = new HashMap<>();
        model.put("fileInfo", fileInfoDto);
        Mail mail = Mail.builder()
                .subject("Uploaded file link")
                .to(fileInfoDto.getOwner().getEmail())
                .build();
        executorService.submit(() -> emailService.sendEmail(mail, "mails/file_upload_link_mail.ftl", model));
    }
}
