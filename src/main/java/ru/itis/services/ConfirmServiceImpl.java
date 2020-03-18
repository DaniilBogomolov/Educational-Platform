package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import java.util.Optional;

@Service
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean confirm(String confirmCode) {
        Optional<User> userCandidate = userRepository.findUserByConfirmationCode(confirmCode);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            user.setConfirmed(true);
            userRepository.update(user);
            return true;
        }
        return false;
    }
}
