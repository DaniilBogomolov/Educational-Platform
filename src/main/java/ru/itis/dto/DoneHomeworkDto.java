package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Homework;
import ru.itis.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoneHomeworkDto {

    private String userLogin;

    private List<HomeworkDescriptionDto> homeworks;

    public static DoneHomeworkDto from(List<Homework> homeworkList, User user) {
        return DoneHomeworkDto.builder()
                .userLogin(user.getLogin())
                .homeworks(homeworkList
                        .stream()
                        .map(HomeworkDescriptionDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
