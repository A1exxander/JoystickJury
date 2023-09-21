package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Repository;
import xyz.joystickjury.backend.utils.iDAO;
import java.sql.SQLException;


@Repository
public interface iUserCredentialsDAO extends iDAO<Integer, UserCredentials> {

    public UserCredentials getByUserID(Integer id) throws SQLException;
    public UserCredentials get(String email) throws SQLException;

}
