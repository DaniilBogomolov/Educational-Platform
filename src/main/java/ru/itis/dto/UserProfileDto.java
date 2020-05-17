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
    private String navbarFirstName;
    private String navbarPhotoLink;
    private String profilePhotoLink;
    private Role role;
    private List<RoomNamesDto> rooms;
    private boolean myProfile;

    public static UserProfileDto from(User user) {
        UserProfileDto profileDto =  from(user, user);
        profileDto.setMyProfile(true);
        return profileDto;
    }

    public static UserProfileDto from(User accessor, User profile) {
        return UserProfileDto.builder()
                .firstName(profile.getFirstName())
                .navbarFirstName(accessor.getFirstName())
                .profilePhotoLink(profile.getProfilePhotoLink())
                .navbarPhotoLink(accessor.getProfilePhotoLink())
                .role(profile.getRole())
                .rooms(profile.getRooms().stream()
                        .map(RoomNamesDto::from)
                        .collect(Collectors.toList()))
                .myProfile(false)
                .build();
    }
}
