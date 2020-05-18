package ru.itis.repositories;

import org.springframework.stereotype.Repository;
import ru.itis.models.Homework;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class HomeworkRepositoryImpl implements HomeworkRepository {

    //language=HQL
    private static final String HQL_FIND_BY_GROUP_ID = "select hw from Homework hw join fetch hw.forGroup room join fetch room.participants where room.identifier in :id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Homework> findAllByForGroup_IdentifierIn(List<String> ids) {
        return entityManager.createQuery(HQL_FIND_BY_GROUP_ID, Homework.class)
                .setParameter("id", ids)
                .getResultList();
    }

    @Override
    public void save(Homework entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Long aLong) {
        entityManager.remove(find(aLong));
    }

    @Override
    public void update(Homework entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<Homework> find(Long aLong) {
        try {
            Homework homework =  entityManager.find(Homework.class, aLong);
            return Optional.ofNullable(homework);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Homework> findAll() {
        return entityManager.createQuery("select hw from Homework hw", Homework.class)
                .getResultList();
    }
}
