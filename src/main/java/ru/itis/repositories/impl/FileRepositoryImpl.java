package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileRepository;
import ru.itis.repositories.UserRepository;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryImpl implements FileRepository {

    @Qualifier("userRepositoryJpaImpl")
    @Autowired
    private UserRepository userRepository;

    //language=PostgreSQL
    private static final String SQL_SAVE_FILE_INFO = "insert into file_info(original_filename, generated_filename, size, type, uploader_id)" +
            "values(?, ?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from file_info";

    //language=SQL
    private static final String SQL_FIND_FILES_BY_USER_ID = "select * from file_info where uploader_id = ?";


    //language=SQL
    private static final String SQL_FIND_FILE_BY_GENERATED_FILENAME = "select * from file_info where generated_filename = ?";

    private RowMapper<FileInfo> rowMapper = (row, rowNumber) -> {
        String generatedFileName = row.getString("generated_filename");
        String type = row.getString("type");
        return FileInfo.builder()
                .id(row.getLong("id"))
                .originalFileName(row.getString("original_filename"))
                .storageFileName(generatedFileName)
                .size(row.getLong("size"))
                .type(type)
                .url("/files/" + generatedFileName)
                .owner(userRepository.find(row.getLong("uploader_id")).get())
                .build();
    };

    private JdbcTemplate jdbcTemplate;

    public FileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void save(FileInfo entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE_FILE_INFO);
            statement.setString(1, entity.getOriginalFileName());
            statement.setString(2, entity.getStorageFileName());
            statement.setLong(3, entity.getSize());
            statement.setString(4, entity.getType());
            statement.setLong(5, entity.getOwner().getId());
            return statement;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(FileInfo entity) {

    }

    @Override
    public Optional<FileInfo> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<FileInfo> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }


    @Override
    public List<FileInfo> getFilesByUploader(Long uploaderId) {
        return jdbcTemplate.query(SQL_FIND_FILES_BY_USER_ID, new Object[]{uploaderId}, rowMapper);
    }

    @Override
    public Optional<FileInfo> getFileByGeneratedFileName(String fileName) {
        try {
            FileInfo fileInfo = jdbcTemplate.queryForObject(SQL_FIND_FILE_BY_GENERATED_FILENAME, new Object[]{fileName}, rowMapper);
            return Optional.ofNullable(fileInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FileInfo> getFilesByUploaderLogin(String login) {
        return null; //TODO
    }
}
