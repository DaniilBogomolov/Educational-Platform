package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Role;
import ru.itis.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    private String firstName;
    private String profilePhotoLink;
    private Role role;

    public static UserProfileDto from(User user) {
        return UserProfileDto.builder()
                .firstName(user.getFirstName())
                .profilePhotoLink(user.getProfilePhotoLink())
                .role(user.getRole())
                .build();
    }
}