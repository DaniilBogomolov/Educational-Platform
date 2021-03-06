package ru.itis.repositories.jpa;

import org.springframework.stereotype.Repository;
import ru.itis.models.Message;
import ru.itis.repositories.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositoryJpaImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    //language=HQL
    private static final String HQL_FIND_ALL_MESSAGES = "select message from Message message left join fetch message.attachment where message.sentFrom.id = :id";

    @Override
    public void save(Message entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(find(id));
    }

    @Override
    public void update(Message entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<Message> find(Long id) {
        return Optional.ofNullable(entityManager.find(Message.class, id));
    }

    @Override
    public List<Message> findAll() {
        return entityManager.createQuery("from Message", Message.class).getResultList();
    }

    @Override
    public List<Message> getAllMessagesForRoom(Long roomId) {
        return entityManager.createQuery(HQL_FIND_ALL_MESSAGES, Message.class)
                .setParameter("id", roomId)
                .getResultList();
    }
}
