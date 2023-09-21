package xyz.joystickjury.backend.user;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;

@Service
public interface iUserService {

    public User getUser(int id) throws SQLException;
    public User getUser(String displayName) throws SQLException;
    public List<User> getAllUsers() throws SQLException;
    public boolean userExists(@NotNull String displayName) throws SQLException;
    public boolean userExists(int userID) throws SQLException;
    public void saveUser(User user) throws SQLException;
    public void updateUser(User user, User updatedUser) throws SQLException;
    public void deleteUser(int id) throws SQLException;
    public boolean isSameUser(User currentUser, User updatedUser);

}
