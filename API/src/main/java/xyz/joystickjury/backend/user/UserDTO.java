package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@AllArgsConstructor @Getter
public class UserDTO { // Even though it is a 1:1 mirror of User, it is still worth doing this to be more consistent, follow best practices, and maintainability (in case User class changes in the future, but we want to maintain this as the user presentation).

    @Null
    private final Integer userID; // Can be null in the case where we are registering a new user who doesn't have an ID yet
    @NotNull
    private final String displayName;
    private final String profilePictureLink;
    private final String profileDescription;
    @NotNull
    private final AcccountType accountType;

}
