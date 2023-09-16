package xyz.joystickjury.backend.user;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface iUserService {

    public User getUser(Integer id) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public void saveUser(User user) throws SQLException;
    public void updateUser(User user, User updatedUser) throws SQLException;
    public void deleteUser(Integer id) throws SQLException;
    public boolean isSameUser(User currentUser, User updatedUser);

}
