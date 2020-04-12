package ru.itis.services;

import ru.itis.dto.RoomInfoDto;
import ru.itis.dto.RoomNamesDto;
import ru.itis.models.Room;
import ru.itis.models.User;

import java.util.List;

public interface RoomService {

    Room createRoom(RoomInfoDto roomInfo);

    RoomNamesDto getRoomByGeneratedName(String generatedName);

    List<RoomNamesDto> getUsersRooms(User user);

    boolean connectToRoom(String roomGeneratedName, Long userId);
}
