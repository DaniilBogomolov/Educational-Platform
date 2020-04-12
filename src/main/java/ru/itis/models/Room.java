package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "room_user_info",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
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
