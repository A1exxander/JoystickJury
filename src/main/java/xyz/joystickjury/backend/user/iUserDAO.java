package xyz.joystickjury.backend.user;

import xyz.joystickjury.backend.utils.iDAO;

import java.sql.SQLException;

public interface iUserDAO extends iDAO<Integer, User> {
    public User get(String email) throws SQLException;
}
