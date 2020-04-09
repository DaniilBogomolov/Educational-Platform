package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long roomId;

    @Column(nullable = false, unique = true)
    private String identifier;

    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @CollectionTable(name = "room_user_info")
    private List<User> participants;

    public void addParticipant(User user) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        participants.add(user);
    }

    public void setOwner(User owner) {
        this.owner = owner;
        addParticipant(owner);
    }
}
