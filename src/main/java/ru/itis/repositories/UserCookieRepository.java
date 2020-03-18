package ru.itis.repositories;

import ru.itis.models.Cookie;
import ru.itis.models.User;

import java.util.Optional;

public interface UserCookieRepository extends CrudRepository<Long, Cookie> {

    Optional<Cookie> findCookieByValue(String value);
}
