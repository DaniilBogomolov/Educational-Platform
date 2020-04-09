package ru.itis.services;

import ru.itis.models.User;

public interface ConfirmService {
    boolean confirm(String confirmCode);


    String getNewConfirmCode(User user);
}
