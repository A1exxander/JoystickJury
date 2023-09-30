package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@AllArgsConstructor @Getter @Setter
public class User {

    @Null @Min(1)
    private Integer userID;
    @NotNull @Min(1) @Max(16)
    private String displayName;
    private String profilePictureLink;
    private String profileDescription;
    private Date registrationDate;
    @NotNull
    private AcccountType accountType;

}
