package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.Cookie;
import ru.itis.repositories.UserCookieRepository;
import ru.itis.repositories.UserRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserCookieRepositoryImpl implements UserCookieRepository {


    //language=SQL
    private static final String SQL_SAVE_NEW_COOKE = "insert into user_cookie(value, user_id) values (?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL_COOKIES = "select * from user_cookie";

    //language=SQL
    private static final String SQL_FIND_COOKIE_BY_ID = "select * from user_cookie where id = ?";

    //language=SQL
    private static final String SQL_UPDATE_COOKIE = "update user_cookie set value = ? where user_id = ?";

    //language=SQL
    private static final String SQL_FIND_COOKIE_BY_VALUE = "select * from user_cookie where value = ?";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Cookie> rowMapper = (row, rowNumber) -> {
        return Cookie.builder()
                .id(row.getLong("id"))
                .value(row.getString("value"))
                .user(userRepository.find(row.getLong("user_id")).get())
                .build();
    };

    @Override
    public void save(Cookie entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_NEW_COOKE);
            statement.setString(1, entity.getValue());
            statement.setLong(2, entity.getUser().getId());
            return statement;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Cookie entity) {
        jdbcTemplate.update(SQL_UPDATE_COOKIE, entity.getValue(), entity.getUser().getId());
    }

    @Override
    public Optional<Cookie> find(Long id) {
        try {
            Cookie cookie = jdbcTemplate.queryForObject(SQL_FIND_COOKIE_BY_ID, new Object[]{id}, rowMapper);
            return Optional.ofNullable(cookie);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Cookie> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_COOKIES, rowMapper);
    }

    @Override
    public Optional<Cookie> findCookieByValue(String value) {
        try {
            Cookie cookie = jdbcTemplate.queryForObject(SQL_FIND_COOKIE_BY_VALUE, new Object[]{value}, rowMapper);
            return Optional.ofNullable(cookie);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
