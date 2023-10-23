package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.exception.ResourceAlreadyExistsException;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;
import xyz.joystickjury.backend.exception.UnauthorizedOperationException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;


@Service
@AllArgsConstructor @NoArgsConstructor
public class UserService implements iUserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUser(int userID) throws SQLException { // Make it take an int not Integer to make sure that this does not get called with a null argument w/o having to check
        User user = userDAO.get(userID);
        if (user == null) { throw new ResourceDoesNotExistException("User ID : " + userID + " does not exist."); }
        return user;
    }

    @Override
    public User getUser(String displayName) throws SQLException {
        if (!userExists(displayName)) { throw new ResourceDoesNotExistException("Username : " + displayName + " does not exist."); } // Do this instead of calling doesUserExist which will call the DAO twice for no real reason
        return userDAO.get(displayName);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    @Override
    public boolean userExists(@NotNull String displayName) throws SQLException {
        return userDAO.get(displayName) != null;
    }

    @Override
    public boolean userExists(int userID) throws SQLException {
        return userDAO.get(userID) != null;
    }

    @Override
    public void updateUser(@NotNull User user, @NotNull User updatedUser) throws SQLException {
        if (!isSameUser(user, updatedUser)) { throw new UnauthorizedOperationException("UserID, DisplayName, RegistrationDate, and AccountType must be the same!"); }
        else if (!userExists(user.getUserID())) { throw new ResourceDoesNotExistException("User ID : " + user.getUserID() + " does not exist."); };
        userDAO.update(updatedUser);
    }

    @Override
    public void saveUser(@NotNull User user) throws SQLException {
        if (user.getUserID() != null) { throw new IllegalArgumentException("User ID : " + user.getUserID() + " already exists."); }
        else if (userExists(user.getDisplayName())) { throw new ResourceAlreadyExistsException("Display name is already taken."); }
        userDAO.save(user);
    }

    @Override
    public void deleteUser(int userID) throws SQLException {
        if (!userExists(userID)) { throw new ResourceDoesNotExistException("User ID : " + userID + " does not exist."); }
        userDAO.delete(userID);
    }

    @Override
    public boolean isSameUser(User currentUser, User updatedUser) { // Determines if both user objects share the same read-only data uniquely identifying the same user
        return ( currentUser.getUserID() == updatedUser.getUserID() && currentUser.getRegistrationDate() == updatedUser.getRegistrationDate() && currentUser.getAccountType() == updatedUser.getAccountType() && currentUser.getDisplayName() == updatedUser.getDisplayName());
    }

}
