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
@Entity(name = "User")
@Table(name = "user_info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "photo")
    private String profilePhotoLink;

    @Column(name = "password")
    private String password;

    @Column(name = "is_confirmed")
    private Boolean confirmed;

    @Column(name = "confirm_code")
    private String confirmCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "participants")
    @ToString.Exclude
    private List<Room> rooms;


    public void addRoom(Room room) {
        if (rooms == null) rooms = new ArrayList<>();
        rooms.add(room);
    }
}
