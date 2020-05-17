package ru.itis.repositories;

import ru.itis.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User> {

    Optional<User> findUserByConfirmationCode(String code);

    Optional<User> findUserByLogin(String login);

}
