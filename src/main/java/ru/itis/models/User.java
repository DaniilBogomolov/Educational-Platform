package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
