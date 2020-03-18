package ru.itis.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.itis.models.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.channels.IllegalSelectorException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String from;

    @Autowired
    private TemplateService templateService;

    @Override
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setFrom(from);
            helper.setText(mail.getText(), true);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendEmail(Mail mail, String templatePath, Map<String, Object> model) {
        Template template = templateService.fromPath(templatePath);
        String text = null;
        try {
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
        mail.setText(text);
        sendEmail(mail);
    }
}
