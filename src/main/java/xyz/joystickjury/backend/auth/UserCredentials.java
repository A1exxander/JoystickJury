package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


@AllArgsConstructor @Getter @Setter
public class UserCredentials { // Should prob have a RawCredentials class & HashedCredentials class that inherit from Credentials, but too much voodoo when mapping

    @Null @Min(1)
    private Integer userID;
    @NotNull @Max(64) @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Invalid Email format")
    private final String email;
    @NotNull @Min(8) @Max(32)
    private final String password;

}
