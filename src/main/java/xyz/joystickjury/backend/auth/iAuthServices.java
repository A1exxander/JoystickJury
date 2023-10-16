package xyz.joystickjury.backend.auth;

import xyz.joystickjury.backend.user.User;

import java.sql.SQLException;

public interface iAuthServices {
    public User login(UserCredentials rawUserCredentials) throws SQLException;
    public int register(UserCredentials rawUserCredentials, User newUser) throws SQLException;
}
