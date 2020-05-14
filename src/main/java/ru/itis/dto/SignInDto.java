package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDto {
    @Pattern(regexp = "[a-zA-Z]+", message = "{wrong}")
    private String login;
    private String password;
}
