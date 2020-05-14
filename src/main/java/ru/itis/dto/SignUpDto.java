package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.itis.models.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {
    @Pattern(regexp = "[a-zA-Z]+")
    private String firstName;
    @Pattern(regexp = "[a-zA-Z]+")
    private String lastName;
    @Email
    @NotNull
    private String email;
    private String login;
    private String password;

    public SignUpDto from(User user) {
        return SignUpDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
