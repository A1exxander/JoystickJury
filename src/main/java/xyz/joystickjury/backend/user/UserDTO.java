package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserDTO { // Even though it is a 1:1 mirror of User, it is still worth doing this to be more consistent, follow best practices, and maintainability (in case User class changes in the future, but we want to maintain this as the user presentation).

    private Integer userID;
    @NotNull
    private String displayName;
    private String profilePictureLink;
    private String profileDescription;
    @NotNull
    private Date registrationDate;
    @NotNull
    private AcccountType accountType;

}
