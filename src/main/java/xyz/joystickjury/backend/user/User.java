package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@Getter @Setter @AllArgsConstructor // No need to worry about Setter existing for non-final values, Lombok handles this
public class User {

    @Null @Min(1)
    private Integer userID;
    @NotNull
    private final String displayName;
    private String profilePictureLink;
    private String profileDescription;
    private Date registrationDate;
    @NotNull
    private final AcccountType accountType;

}
