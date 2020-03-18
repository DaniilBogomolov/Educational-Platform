package ru.itis.services;


import ru.itis.models.Mail;

import java.util.Map;

public interface EmailService {

    void sendEmail(Mail mail);

    void sendEmail(Mail mail, String templatePath, Map<String, Object> model);
}
