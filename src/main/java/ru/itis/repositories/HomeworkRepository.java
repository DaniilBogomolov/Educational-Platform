package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.Homework;

import java.util.List;

public interface HomeworkRepository extends CrudRepository<Long, Homework> {

    List<Homework> findAllByForGroup_IdentifierIn(List<String> ids);
}
