package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.RoomInfoDto;
import ru.itis.dto.RoomNamesDto;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.repositories.RoomRepository;
import ru.itis.services.RoomService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public Room createRoom(RoomInfoDto roomInfo) {
        String identifier = UUID.randomUUID().toString();
        Room room = Room.builder()
                .identifier(identifier)
                .name(roomInfo.getName())
                .build();
        room.setOwner(roomInfo.getOwner());
        roomRepository.save(room);
        return room;
    }

    @Override
    public RoomNamesDto getRoomByGeneratedName(String generatedName) {
        return RoomNamesDto.from(roomRepository.findByGeneratedName(generatedName));
    }

    @Override
    @Transactional
    public List<RoomNamesDto> getUsersRooms(User user) {
        return roomRepository.findAll().stream()
                .filter(room -> room.getParticipants().contains(user))
                .map(RoomNamesDto::from)
                .collect(Collectors.toList());
    }
}
