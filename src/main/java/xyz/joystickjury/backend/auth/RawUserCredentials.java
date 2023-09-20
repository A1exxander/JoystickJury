package xyz.joystickjury.backend.auth;

import lombok.Getter;
import javax.validation.constraints.*;

@Getter
public class RawUserCredentials extends UserCredentials {

    @NotNull @Min(8) @Max(32)
    private final String password;

    public RawUserCredentials(@NotNull @Max(64) @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Invalid Email format") String email, String password) {
        super(email);
        this.password = password;
    }

}
