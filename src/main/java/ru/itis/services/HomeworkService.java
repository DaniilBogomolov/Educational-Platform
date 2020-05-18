package ru.itis.services;

import ru.itis.dto.HomeworkAssignmentDto;
import ru.itis.dto.HomeworkDescriptionDto;
import ru.itis.dto.HomeworkPageInfo;
import ru.itis.models.Homework;
import ru.itis.models.User;

import java.util.List;

public interface HomeworkService {

    void createHomework(HomeworkAssignmentDto assignment);

    List<HomeworkDescriptionDto> findAllByUserLogin(String login);

    List<HomeworkDescriptionDto> findAllNotDoneByUserLogin(String login);

    void accept(HomeworkAssignmentDto homework);

    HomeworkPageInfo getInfo(String roomId, String userLogin);


}
