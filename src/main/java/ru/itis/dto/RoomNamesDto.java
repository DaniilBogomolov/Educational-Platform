package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Room;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomNamesDto {
    private String originalName;
    private String generatedName;

    public static RoomNamesDto from(Room room) {
        return RoomNamesDto.builder()
                .originalName(room.getName())
                .generatedName(room.getIdentifier())
                .build();
    }
}
