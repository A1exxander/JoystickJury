package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter @Setter @AllArgsConstructor // No need to worry about Setter existing for non-final values, Lombok handles this
public class User {

    private final Integer userID;
    @NotNull
    private String displayName;
    private String profilePictureLink;
    private String profileDescription;
    @NotNull
    private final Date registrationDate;
    @NotNull
    private final AcccountType accountType;

}
