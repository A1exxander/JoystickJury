package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.cexceptions.EmailMismatchException;

import java.sql.SQLException;

@Service
public interface iHashedCredentialsService {
    public HashedUserCredentials getHashedUserCredentials(RawUserCredentials userCredentials) throws SQLException;
    public void updateUserCredentials(HashedUserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException;
    public HashedUserCredentials hashUserCredentials(RawUserCredentials userCredentials);
    public boolean areValidCredentials(RawUserCredentials userCredentials, HashedUserCredentials hashedUserCredentials) throws EmailMismatchException;
}
