package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.itis.dto.RoomInfoDto;
import ru.itis.dto.RoomNamesDto;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.repositories.RoomRepository;
import ru.itis.repositories.UserRepository;
import ru.itis.services.RoomService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    @Qualifier("userRepositoryJpaImpl")
    private UserRepository userRepository;

    @Override
    @Transactional
    public Room createRoom(RoomInfoDto roomInfo) {
        String identifier = UUID.randomUUID().toString();
        Room room = Room.builder()
                .identifier(identifier)
                .name(roomInfo.getName())
                .build();
        room.setOwner(userRepository.find(roomInfo.getOwner().getId()).get());
        roomRepository.save(room);
        return room;
    }

    @Override
    public RoomNamesDto getRoomNamesByGeneratedName(String generatedName) {
        return RoomNamesDto.from(getRoomByGeneratedName(generatedName));
    }

    @Override
    @Transactional
    public List<RoomNamesDto> getUsersRooms(User user) {
        return roomRepository.findAll().stream()
                .filter(room -> room.getParticipants().contains(user))
                .map(RoomNamesDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public Room getRoomByGeneratedName(String generatedName) {
        return roomRepository.findByGeneratedName(generatedName);
    }

    @Override
    @Transactional
    public boolean connectToRoom(String roomGeneratedName, Long userId) {
        Room room = roomRepository.findByGeneratedName(roomGeneratedName);
        Optional<User> userCandidate = userRepository.find(userId);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (user.getRooms() != null && user.getRooms().contains(room)) {
                return false;
            }
            room.addParticipant(user);
            user.addRoom(room);
            roomRepository.update(room);
            userRepository.update(user);
            return true;
        }
        throw new AccessDeniedException("No user found");
    }
}
