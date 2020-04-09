package ru.itis.services;

import ru.itis.dto.UserProfileDto;
import ru.itis.models.User;

import java.util.Optional;

public interface UserService {

    UserProfileDto saveUserProfilePhotoInDB(User user);

    User getUserById(Long userId);
}
