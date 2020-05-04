package ru.itis.repositories.jpa;

import org.springframework.stereotype.Repository;
import ru.itis.models.FileInfo;
import ru.itis.repositories.FileRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public class FileRepositoryJpaImpl implements FileRepository {

    //language=HQL
    private static final String HQL_FIND_BY_GENERATE_NAME = "select file_info from FileInfo file_info where file_info.storageFileName = :name";

    //language=HQL
    public static final String HQL_FIND_BY_UPLOADER_NAME = "select file_info from FileInfo file_info join User user on file_info.owner = user where user.login = :name";

    //language=HQL
    public static final String HQL_FIND_BY_UPLOADER_ID = "select file_info from FileInfo file_info join User user on file_info.owner = user where user.id = :id";

    //language=HQL
    private static final String HQL_FIND_BY_ID = "select file_info from FileInfo file_info where file_info.id = :id";

    //language=HQL
    private static final String HQL_FIND_ALL = "from FileInfo";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FileInfo> getFilesByUploader(Long uploaderId) {
        return entityManager.createQuery(HQL_FIND_BY_UPLOADER_ID, FileInfo.class)
                .setParameter("id", uploaderId)
                .getResultList();
    }

    @Override
    public Optional<FileInfo> getFileByGeneratedFileName(String fileName) {
        return Optional.ofNullable(entityManager.createQuery(HQL_FIND_BY_GENERATE_NAME, FileInfo.class)
                .setParameter("name", fileName)
                .getSingleResult());
    }

    @Override
    public List<FileInfo> getFilesByUploaderLogin(String login) {
        return entityManager.createQuery(HQL_FIND_BY_UPLOADER_NAME, FileInfo.class)
                .setParameter("name", login)
                .getResultList();
    }

    @Override
    public void save(FileInfo entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(find(id));
    }

    @Override
    public void update(FileInfo entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<FileInfo> find(Long id) {
        return Optional.ofNullable(entityManager.createQuery(HQL_FIND_BY_ID, FileInfo.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public List<FileInfo> findAll() {
        return entityManager.createQuery(HQL_FIND_ALL, FileInfo.class)
                .getResultList();
    }
}
