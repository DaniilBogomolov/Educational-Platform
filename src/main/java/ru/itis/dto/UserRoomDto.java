package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Room;
import ru.itis.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoomDto {

    private String firstName;

    private String fullName;

    private String profilePhotoLink;

    private boolean owner;

    private String originalRoomName;

    private String generatedRoomName;

    public static UserRoomDto from(User user, RoomNamesDto roomNamesDto) {
        return UserRoomDto.builder()
                .firstName(user.getFirstName())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .profilePhotoLink(user.getProfilePhotoLink())
                .owner(user.getRooms().stream()
                        .filter(room -> room.getIdentifier().equals(roomNamesDto.getGeneratedName()))
                        .anyMatch(room -> room.getOwner().equals(user)))
                .originalRoomName(roomNamesDto.getOriginalName())
                .generatedRoomName(roomNamesDto.getGeneratedName())
                .build();
    }
}
