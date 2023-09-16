package xyz.joystickjury.backend.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter @Setter // No need to worry about Setter existing for non-final values, Lombok handles this
public class User {

    private final Integer userID;
    private String email;
    private String profilePictureLink;
    private String profileDescription;
    private final Date registrationDate;
    private final AcccountType accountType;

    public User(Integer userID, String email, String profilePictureLink, String profileDescription, Date registrationDate, AcccountType accountType) {

        if (email == null || registrationDate == null || accountType == null ){
            throw new IllegalArgumentException("Email, Registration Date, & Account Type cannot be null!");
        }

        this.userID = userID;
        this.email = email;
        this.profilePictureLink = profilePictureLink;
        this.profileDescription = profileDescription;
        this.registrationDate = registrationDate;
        this.accountType = accountType;

    }

}
