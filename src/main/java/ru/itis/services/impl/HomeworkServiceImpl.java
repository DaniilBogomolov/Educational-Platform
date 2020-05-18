package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.itis.dto.DoneHomeworkDto;
import ru.itis.dto.HomeworkAssignmentDto;
import ru.itis.dto.HomeworkDescriptionDto;
import ru.itis.dto.HomeworkPageInfo;
import ru.itis.models.Homework;
import ru.itis.models.Room;
import ru.itis.models.User;
import ru.itis.repositories.HomeworkRepository;
import ru.itis.repositories.RoomRepository;
import ru.itis.repositories.UserRepository;
import ru.itis.services.FileService;
import ru.itis.services.HomeworkService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Qualifier("userRepositoryJpaImpl")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public void createHomework(HomeworkAssignmentDto assignment) {
        homeworkRepository.save(createHomework(assignment, false));
    }

    @Override
    @Transactional
    public List<HomeworkDescriptionDto> findAllNotDoneByUserLogin(String login) {
        return findAllHomeworksByUserLogin(login)
                .stream()
                .filter(homework -> !homework.isDone())
                .map(HomeworkDescriptionDto::from)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void accept(HomeworkAssignmentDto assignmentDto) {
        homeworkRepository.save(createHomework(assignmentDto, true));
    }

    @Override
    public HomeworkPageInfo getInfo(String roomId, String userLogin) {
        return HomeworkPageInfo.builder()
                .roomIdentifier(roomId)
                .senderLogin(userLogin)
                .files(fileService.findFilesByUserLogin(userLogin))
                .build();
    }

    @Override
    public List<DoneHomeworkDto> getDoneHomeworksForRoom(String roomId) {
        Map<User, List<Homework>> map = new HashMap<>();
        List<Homework> homeworks = homeworkRepository.findAllByForGroup_IdentifierIn(List.of(roomId))
                .stream()
                .filter(Homework::isDone)
                .distinct()
                .collect(Collectors.toList());
        for (Homework homework : homeworks) {
            if (map.containsKey(homework.getSender())) {
                map.get(homework.getSender()).add(homework);
            } else {
                List<Homework> usersHomeworks = new ArrayList<>();
                usersHomeworks.add(homework);
                map.put(homework.getSender(), usersHomeworks);
            }
        }
        List<DoneHomeworkDto> dto = new ArrayList<>();
        for (User user : map.keySet()) {
            dto.add(DoneHomeworkDto.from(map.get(user), user));
        }
        return dto;
    }

    @Override
    @Transactional
    public List<HomeworkDescriptionDto> findAllByUserLogin(String login) {
        return findAllHomeworksByUserLogin(login)
                .stream()
                .map(HomeworkDescriptionDto::from)
                .distinct()
                .collect(Collectors.toList());
    }

    private Homework createHomework(HomeworkAssignmentDto assignment, boolean isDone) {
        return Homework.builder()
                .text(assignment.getText())
                .sender(userRepository.findUserByLogin(assignment.getSenderLogin()).orElseThrow())
                .isDone(isDone)
                .attachment(fileService.getFileByFullURL(assignment.getAttachment().getUrl()))
                .forGroup(roomRepository.findByGeneratedName(assignment.getRoomIdentifier()))
                .build();
    }


    private List<Homework> findAllHomeworksByUserLogin(String login) {
        return homeworkRepository.findAllByForGroup_IdentifierIn(userRepository.findUserByLogin(login).get().getRooms().stream()
                .map(Room::getIdentifier)
                .collect(Collectors.toList()));
    }
}
