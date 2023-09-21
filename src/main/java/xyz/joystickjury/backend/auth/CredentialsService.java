package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.exception.EmailMismatchException;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;


@Service
@AllArgsConstructor
public class CredentialsService implements iCredentialsService {

    @Autowired
    private final UserCredentialsDAO userCredentialsDAO;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Use BCrypt by default

    @Override
    public UserCredentials getHashedUserCredentials(@NotNull String email) throws SQLException { // Used for logging in, not registering a new user!

        UserCredentials hashedUserCredentials = userCredentialsDAO.get(email);

        if (hashedUserCredentials == null) {
            throw new ResourceDoesNotExistException("Invalid request. No user with Email : " + email + " was found");
        }
        else {
            return hashedUserCredentials;
        }

    }

    @Override
    public UserCredentials createHashedUserCredentials(@NotNull UserCredentials userCredentials) { // Used for registration, not logging in!
        return createHashedUserCredentials(null, userCredentials);
    }

    @Override
    public UserCredentials createHashedUserCredentials(Integer userID, @NotNull UserCredentials userCredentials) {
        String hashedPassword = passwordEncoder.encode(userCredentials.getPassword());
        return new UserCredentials(userID, userCredentials.getEmail(), hashedPassword);
    }

    @Override
    public void saveCredentials(@NotNull UserCredentials hashedUserCredentials) throws SQLException {
        if (hashedUserCredentials.getUserID() == null) { throw new IllegalArgumentException("Invalid request. UserID inside of UserCredentials cannot be NULL."); }
        userCredentialsDAO.save(hashedUserCredentials);
    }

    @Override
    public boolean emailExists(@NotNull String email) throws SQLException {
        return userCredentialsDAO.get(email) != null;
    }

    @Override
    public boolean areValidCredentials(@NotNull UserCredentials userCredentials, @NotNull UserCredentials hashedUserCredentials) {
        if (!userCredentials.getEmail().equals(hashedUserCredentials.getEmail())) { throw new EmailMismatchException("Invalid request. Email in UserCredentials : " + userCredentials + "does not match email in HashedUserCredentials : " + hashedUserCredentials.getEmail()); }
        return (BCrypt.checkpw(userCredentials.getPassword(), hashedUserCredentials.getPassword()));
    }

    @Override
    public void updateUserCredentials(@NotNull UserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException {
        if (hashedUserCredentials.getUserID() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        else if ( userCredentialsDAO.getByUserID(hashedUserCredentials.getUserID()) == null) { throw new ResourceDoesNotExistException("Invalid request. UserID does not exist."); }
        userCredentialsDAO.update(hashedUserCredentials);
    }



}