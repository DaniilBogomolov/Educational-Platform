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
public class RoomInfoDto {
    private String name;
    private User owner;

    public static RoomInfoDto from(Room room) {
        return RoomInfoDto.builder()
                .name(room.getName())
                .owner(room.getOwner())
                .build();
    }
}
