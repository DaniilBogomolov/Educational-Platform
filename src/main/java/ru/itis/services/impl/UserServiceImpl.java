package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserProfileDto;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;
import ru.itis.services.UserService;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userRepositoryJpaImpl")
    private UserRepository userRepository;

    public UserProfileDto saveUserProfilePhotoInDB(User user) {
        userRepository.update(user);
        return UserProfileDto.from(user);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userCandidate = userRepository.find(userId);
        if (userCandidate.isPresent()) {
            return userCandidate.get();
        } throw new IllegalArgumentException("No user found!");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
