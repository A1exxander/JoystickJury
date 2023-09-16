package xyz.joystickjury.backend.user;

import xyz.joystickjury.backend.utils.iDAO;

import java.sql.SQLException;

public interface iUserDAO extends iDAO<User, Integer> {
    User get(String email) throws SQLException;
}
