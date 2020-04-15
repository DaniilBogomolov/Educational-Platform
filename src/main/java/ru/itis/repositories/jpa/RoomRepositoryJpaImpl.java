package ru.itis.repositories.jpa;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.models.Room;
import ru.itis.repositories.RoomRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryJpaImpl implements RoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String HQL_FIND_BY_IDENTIFIER = "from Room room join fetch room.participants where room.identifier = :iden";


    //language=HQL
    public static final String HQL_FIND_ALL_ROOMS = "from Room";

    @Override
    public void save(Room entity) {
        entityManager.persist(entity);

        Hibernate.initialize(entity.getOwner());
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(find(id));
    }

    @Override
    public void update(Room entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<Room> find(Long id) {
        return Optional.ofNullable(entityManager.find(Room.class, id));
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery(HQL_FIND_ALL_ROOMS, Room.class)
                .getResultList();
    }

    @Override
    public Room findByGeneratedName(String generatedName) {
        return entityManager.createQuery(HQL_FIND_BY_IDENTIFIER, Room.class)
                .setParameter("iden", generatedName)
                .getSingleResult();
    }
}
