package user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Component
public class User {

    private Integer userID;
    private String email;
    private String profilePictureLink;
    private String profileDescription;
    private Date registrationDate;
    private UserType accountType;

}
