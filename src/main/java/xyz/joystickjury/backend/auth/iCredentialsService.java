package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.cexceptions.EmailMismatchException;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

@Service
public interface iCredentialsService {

    HashedUserCredentials getHashedUserCredentials(@NotNull String email) throws SQLException;
    HashedUserCredentials createHashedUserCredentials(@NotNull RawUserCredentials userCredentials);
    public HashedUserCredentials createHashedUserCredentials(@NotNull RawUserCredentials userCredentials, Integer userID);
    void saveCredentials(@NotNull HashedUserCredentials hashedUserCredentials) throws SQLException;
    boolean emailExists(String email) throws SQLException;
    boolean areValidCredentials(@NotNull RawUserCredentials userCredentials, @NotNull HashedUserCredentials hashedUserCredentials);
    void updateUserCredentials(@NotNull HashedUserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException;
}
