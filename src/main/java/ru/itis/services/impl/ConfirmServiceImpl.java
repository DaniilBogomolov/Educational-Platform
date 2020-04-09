package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;
import ru.itis.services.ConfirmService;

import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    @Qualifier("userRepositoryJpaImpl")
    private UserRepository userRepository;

    @Override
    public boolean confirm(String confirmCode) {
        Optional<User> userCandidate = userRepository.findUserByConfirmationCode(confirmCode);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            user.setConfirmed(true);
            user.setRole(Role.STUDENT);
            userRepository.update(user);
            return true;
        }
        return false;
    }

    @Override
    public String getNewConfirmCode(User user) {
        String newIdentifier = UUID.randomUUID().toString();
        user.setConfirmCode(newIdentifier);
        userRepository.update(user);
        return newIdentifier;
    }
}
