package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.cexceptions.EmailMismatchException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;


@Service
public interface iCredentialsService {

    public HashedUserCredentials getHashedUserCredentials(@NotNull String email) throws SQLException;
    public HashedUserCredentials createHashedUserCredentials(@NotNull RawUserCredentials userCredentials);
    public HashedUserCredentials createHashedUserCredentials(Integer userID, @NotNull RawUserCredentials userCredentials);
    public void saveCredentials(@NotNull HashedUserCredentials hashedUserCredentials) throws SQLException;
    public boolean emailExists(String email) throws SQLException;
    public boolean areValidCredentials(@NotNull RawUserCredentials userCredentials, @NotNull HashedUserCredentials hashedUserCredentials);
    public void updateUserCredentials(@NotNull HashedUserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException;

}
