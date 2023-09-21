package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.exception.EmailMismatchException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;


@Service
public interface iCredentialsService {

    public UserCredentials getHashedUserCredentials(@NotNull String email) throws SQLException;
    public UserCredentials createHashedUserCredentials(@NotNull UserCredentials userCredentials);
    public UserCredentials createHashedUserCredentials(Integer userID, @NotNull UserCredentials userCredentials);
    public void saveCredentials(@NotNull UserCredentials hashedUserCredentials) throws SQLException;
    public boolean emailExists(String email) throws SQLException;
    public boolean areValidCredentials(@NotNull UserCredentials userCredentials, @NotNull UserCredentials hashedUserCredentials);
    public void updateUserCredentials(@NotNull UserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException;

}
