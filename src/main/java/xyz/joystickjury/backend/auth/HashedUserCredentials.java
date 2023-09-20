package xyz.joystickjury.backend.auth;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class HashedUserCredentials extends UserCredentials{

    @NotNull @Min(32)
    private final String passwordSalt;

    public HashedUserCredentials(@Null Integer userID, @NotNull @Max(64) @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Invalid Email format") String email, @NotNull @Min(8) @Max(32) String password, String passwordSalt) {
        super(userID, email, password);
        this.passwordSalt = passwordSalt;
    }
}
