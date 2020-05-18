package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
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
import java.util.List;
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
