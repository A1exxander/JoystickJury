package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.cexceptions.EmailMismatchException;
import xyz.joystickjury.backend.cexceptions.ResourceDoesNotExistException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;


@Service
@AllArgsConstructor @NoArgsConstructor
public class CredentialsService implements iCredentialsService {

    @Autowired
    private HashedUserCredentialsDAO hashedUserCredentialsDAO;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Use BCrypt by default

    @Override
    public HashedUserCredentials getHashedUserCredentials(@NotNull String email) throws SQLException { // Used for logging in, not registering a new user!

        HashedUserCredentials hashedUserCredentials = hashedUserCredentialsDAO.get(email);

        if (hashedUserCredentials == null) {
            throw new ResourceDoesNotExistException("Invalid request. No user with Email : " + email + " was found");
        }
        else {
            return hashedUserCredentials;
        }

    }

    @Override
    public HashedUserCredentials createHashedUserCredentials(@NotNull RawUserCredentials userCredentials) { // Used for registration, not logging in!
        return createHashedUserCredentials(userCredentials, null);
    }

    @Override
    public HashedUserCredentials createHashedUserCredentials(@NotNull RawUserCredentials userCredentials, Integer userID) {
        String hashedPassword = passwordEncoder.encode(userCredentials.getPassword());
        return new HashedUserCredentials(userID, userCredentials.getEmail(), hashedPassword);
    }

    @Override
    public void saveCredentials(@NotNull HashedUserCredentials hashedUserCredentials) throws SQLException {
        if (hashedUserCredentials.getUserID() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        hashedUserCredentialsDAO.save(hashedUserCredentials);
    }

    @Override
    public boolean emailExists(@NotNull String email) throws SQLException {
        return hashedUserCredentialsDAO.get(email) != null;
    }

    @Override
    public boolean areValidCredentials(@NotNull RawUserCredentials userCredentials, @NotNull HashedUserCredentials hashedUserCredentials) {
        if (!userCredentials.getEmail().equals(hashedUserCredentials.getEmail())) { throw new EmailMismatchException("Invalid request. Email in UserCredentials : " + userCredentials + "does not match email in HashedUserCredentials : " + hashedUserCredentials.getEmail()); }
        return (BCrypt.checkpw(userCredentials.getPassword(), hashedUserCredentials.getHashedPassword()));
    }

    @Override
    public void updateUserCredentials(@NotNull HashedUserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException {

        if (hashedUserCredentials.getUserID() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        else if ( hashedUserCredentialsDAO.getByUserID(hashedUserCredentials.getUserID()) == null) { throw new ResourceDoesNotExistException("Invalid request. UserID does not exist."); }
        hashedUserCredentialsDAO.update(hashedUserCredentials);

    }



}
