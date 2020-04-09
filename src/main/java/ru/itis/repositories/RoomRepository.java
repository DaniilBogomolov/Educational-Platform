package ru.itis.repositories;

import ru.itis.dto.RoomNamesDto;
import ru.itis.models.Room;

import java.util.List;

public interface RoomRepository extends CrudRepository<Long, Room> {
    Room findByGeneratedName(String generatedName);
}
