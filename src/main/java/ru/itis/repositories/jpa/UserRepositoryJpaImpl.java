package ru.itis.repositories.jpa;

import org.springframework.stereotype.Component;
import ru.itis.models.User;
import ru.itis.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJpaImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    //language=HQL
    public static final String HQL_FIND_USER_BY_CONFIRMATION_CODE = "from User user where user.confirmCode = :confirm";

    //language=HQL
    public static final String HQL_FIND_USER_BY_LOGIN = "from User user where user.login = :login";

    //language=HQL
    public static final String HQL_FIND_ALL_USERS = "from User";

    @Override
    public Optional<User> findUserByConfirmationCode(String code) {
        try {
            User user = entityManager.createQuery(HQL_FIND_USER_BY_CONFIRMATION_CODE, User.class)
                    .setParameter("confirm", code)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try {
            User user = entityManager.createQuery(HQL_FIND_USER_BY_LOGIN, User.class)
                    .setParameter("login", login)
                    .getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void save(User entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(find(id));
    }

    @Override
    @Transactional
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<User> find(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery(HQL_FIND_ALL_USERS, User.class)
                .getResultList();
    }
}
