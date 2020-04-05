package ru.itis.services;

import ru.itis.dto.UserProfileDto;
import ru.itis.models.User;

public interface UserService {

    UserProfileDto saveUserProfilePhotoInDB(User user);
}
