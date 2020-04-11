package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Role;
import ru.itis.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    private String firstName;
    private String profilePhotoLink;
    private Role role;
    private List<RoomNamesDto> rooms;

    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .profilePhotoLink(user.getProfilePhotoLink())
                .role(user.getRole())
                .rooms(user.getRooms().stream()
                        .map(RoomNamesDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
