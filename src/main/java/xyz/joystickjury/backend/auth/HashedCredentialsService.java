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
import java.sql.SQLException;


@Service
@AllArgsConstructor @NoArgsConstructor
public class HashedCredentialsService implements iHashedCredentialsService {

    @Autowired
    private HashedUserCredentialsDAO hashedUserCredentialsDAO;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Use BCrypt by default

    @Override
    public HashedUserCredentials getHashedUserCredentials(RawUserCredentials userCredentials) throws SQLException { // Use for logging, not registering a new user!

        if (userCredentials == null || userCredentials.getEmail() == null || userCredentials.getPassword() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }

        HashedUserCredentials hashedUserCredentials = hashedUserCredentialsDAO.get(userCredentials.getEmail());

        if (hashedUserCredentials == null) {
            throw new ResourceDoesNotExistException("Invalid request. No user with Email : " + userCredentials.getEmail() + " was found");
        }
        else {
            return hashedUserCredentials;
        }

    }

    @Override
    public void updateUserCredentials(HashedUserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException {

        if (hashedUserCredentials == null || hashedUserCredentials.getUserID() == null ||hashedUserCredentials.getEmail() == null || hashedUserCredentials.getHashedPassword() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        else if ( hashedUserCredentialsDAO.getByUserID(hashedUserCredentials.getUserID()) == null) { throw new ResourceDoesNotExistException("Invalid request. UserID does not exist."); }
        hashedUserCredentialsDAO.update(hashedUserCredentials);

    }

    @Override
    public HashedUserCredentials hashUserCredentials(RawUserCredentials userCredentials) { // Use for registration, not logging in!

        if (userCredentials == null || userCredentials.getEmail() == null || userCredentials.getPassword() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        String hashedPassword = passwordEncoder.encode(userCredentials.getPassword());
        return new HashedUserCredentials(null, userCredentials.getEmail(), hashedPassword);

    }

    @Override
    public boolean areValidCredentials(RawUserCredentials userCredentials, HashedUserCredentials hashedUserCredentials) {

        if (userCredentials == null || userCredentials.getEmail() == null || userCredentials.getPassword() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        else if (hashedUserCredentials == null || hashedUserCredentials.getEmail() == null || hashedUserCredentials.getHashedPassword() == null) { throw new IllegalArgumentException("Invalid request. UserCredentials cannot be NULL."); }
        else if (!userCredentials.getEmail().equals(hashedUserCredentials.getEmail())) { throw new EmailMismatchException("Invalid request. Email in UserCredentials : " + userCredentials + "does not match email in HashedUserCredentials : " + hashedUserCredentials.getEmail()); }
        return (BCrypt.checkpw(userCredentials.getPassword(), hashedUserCredentials.getHashedPassword()));

    }

}
