package xyz.joystickjury.backend.auth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.joystickjury.backend.user.User;

import javax.validation.constraints.*;


@Getter
public class HashedUserCredentials extends UserCredentials {

    @Null
    private Integer userID;
    @NotNull
    private final String hashedPassword;

    public HashedUserCredentials(@Null Integer userID, @NotNull @Max(64) @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Invalid Email format") String email, String hashedPassword) {
        super(email);
        this.userID = userID;
        this.hashedPassword = hashedPassword;
    }
}
