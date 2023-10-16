package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.joystickjury.backend.exception.InvalidCredentialsException;
import xyz.joystickjury.backend.exception.ResourceAlreadyExistsException;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.UserService;
import java.sql.SQLException;


@Service
@AllArgsConstructor
public class AuthService implements iAuthServices{

    @Autowired
    private final UserCredentialsService userCredentialsService;
    @Autowired
    private final UserService userService;

    @Override
    public User login(UserCredentials rawUserCredentials) throws SQLException {

        UserCredentials hashedUserCredentials = userCredentialsService.getHashedUserCredentials(rawUserCredentials.getEmail());

        if (!userCredentialsService.areValidCredentials(rawUserCredentials, hashedUserCredentials)){
            throw new InvalidCredentialsException("Invalid request. User credentials are invalid or do not exist"); // Do not explicitly tell the user which for security purposes
        }

        return userService.getUser(hashedUserCredentials.getUserID());

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class) // Make our method execute our 2 seperate database queries as an atomic transaction
    public int register(UserCredentials rawUserCredentials, User newUser) throws SQLException {

        if (userCredentialsService.emailExists(rawUserCredentials.getEmail())) {
            throw new ResourceAlreadyExistsException("Invalid request. Email address already exists");
        }
        else if (userService.userExists(newUser.getDisplayName())) {
            throw new ResourceAlreadyExistsException("Invalid request. Display name already exists");
        }

        userService.saveUser(newUser);
        int userID = userService.getUser(newUser.getDisplayName()).getUserID();
        rawUserCredentials.setUserID(userID);

        userCredentialsService.saveCredentials(userCredentialsService.createHashedUserCredentials(rawUserCredentials));

        return userID;

    }

}
