package xyz.joystickjury.backend.auth;

import org.springframework.stereotype.Repository;
import xyz.joystickjury.backend.utils.iDAO;

import java.sql.SQLException;

@Repository
public interface iHashedUserCredentialsDAO extends iDAO<Integer, HashedUserCredentials> {
    public HashedUserCredentials get(String email) throws SQLException;
}
