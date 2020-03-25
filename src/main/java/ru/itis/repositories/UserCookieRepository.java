package ru.itis.repositories;

import ru.itis.models.Cookie;

import java.util.Optional;

public interface UserCookieRepository extends CrudRepository<Long, Cookie> {

    Optional<Cookie> findCookieByValue(String value);
}
