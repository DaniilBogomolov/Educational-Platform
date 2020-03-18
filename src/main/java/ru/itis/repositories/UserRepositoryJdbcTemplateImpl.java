package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJdbcTemplateImpl implements UserRepository {

    //language=SQL
    private static final String SQL_SAVE_NEW_USER = "insert into user_info(first_name, last_name, email, login, password, confirm_code) values (?, ?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_DELETE_USER = "delete from user_info where id = ?";

    //language=SQL
    private static final String SQL_FIND_USER_BY_ID = "select * from user_info where id = ?";

    //language=SQL
    private static final String SQL_FIND_ALL_USERS = "select * from user_info";

    //language=SQL
    private static final String SQL_FIND_USER_BY_CONFIRM_CODE = "select * from user_info where confirm_code = ?";

    //language=SQL
    private static final String SQL_UPDATE_USER = "update user_info set first_name = ?, last_name = ?, password = ?," +
            " login = ?, email = ?, is_confirmed = ?, confirm_code = ? where id = ?";

    //language=SQL
    private static final String SQL_FIND_USER_BY_LOGIN = "select * from user_info where login = ?";

    private JdbcTemplate jdbcTemplate;

    public RowMapper<User> rowMapper = (row, rowNumber) -> {
        return User.builder()
                .id(row.getLong("id"))
                .firstName(row.getString("first_name"))
                .lastName(row.getString("last_name"))
                .password(row.getString("password"))
                .login(row.getString("login"))
                .email(row.getString("email"))
                .confirmed(row.getBoolean("is_confirmed"))
                .confirmCode(row.getString("confirm_code"))
                .build();
    };

    @Autowired
    public UserRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_NEW_USER);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getLogin());
            statement.setString(5, entity.getPassword());
            statement.setString(6, entity.getConfirmCode());
            return statement;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_USER, id);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(SQL_UPDATE_USER, user.getFirstName(), user.getLastName(), user.getPassword(),
                user.getLogin(), user.getEmail(), user.getConfirmed(), user.getConfirmCode(), user.getId());
    }

    @Override
    public Optional<User> find(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, new Object[]{id}, rowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByConfirmationCode(String code) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_CONFIRM_CODE, new Object[]{code}, rowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_LOGIN, new Object[]{login}, rowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_USERS, rowMapper);
    }
}
