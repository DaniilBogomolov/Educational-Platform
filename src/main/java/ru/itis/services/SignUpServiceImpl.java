package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignUpDto;
import ru.itis.models.Mail;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExecutorService executorService;


    @Override
    public boolean signUp(SignUpDto signUpDto) {
        String identifier = UUID.randomUUID().toString();
        User user = User.builder()
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .confirmCode(identifier)
                .login(signUpDto.getLogin())
                .build();
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            return false;
        }
        Map<String, Object> model = new HashMap<>();
        model.put("identificator", identifier);
        model.put("firstName", signUpDto.getFirstName());
        model.put("lastName", signUpDto.getLastName());
        model.put("email", signUpDto.getEmail());
        Mail confirmMail = Mail.builder()
                .subject("Confirm email address")
                .to(signUpDto.getEmail())
                .build();
        executorService.submit(() -> emailService.sendEmail(confirmMail, "mails/confirmation_mail.ftl", model));
        return true;
    }
}
