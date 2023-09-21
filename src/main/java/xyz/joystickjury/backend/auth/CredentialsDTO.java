package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@AllArgsConstructor @Getter
public class CredentialsDTO {

    @NotNull @Max(64) @Pattern (regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Invalid Email format")
    private final String email;
    @NotNull @Min(8) @Max(32)
    private final String password;

}
