package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.cexceptions.ResourceDoesNotExistException;
import xyz.joystickjury.backend.cexceptions.UnauthorizedOperationException;
import xyz.joystickjury.backend.email.EmailAddressValidator;

import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor @NoArgsConstructor
public class UserService implements iUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User getUser(int userID) throws SQLException { // Make it take an int not Integer to make sure that this does not get called with a null argument w/o having to check
        User user = userDAO.get(userID);
        if (user == null) { throw new ResourceDoesNotExistException("Invalid get request. User ID : " + userID + " does not exist."); } // Do this instead of calling doesUserExist which will call the DAO twice for no real reason
        return user;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    @Override
    public void updateUser(User user, User updatedUser) throws SQLException {

        if (user == null || updatedUser == null) { throw new IllegalArgumentException("User and updated user cannot be null."); }
        else if (isSameUser(user, updatedUser)) { throw new UnauthorizedOperationException("Invalid update request. UserID, DisplayName, RegistrationDate, and AccountType must be the same!"); }
        else if (!userExists(user.getUserID())) { throw new ResourceDoesNotExistException("Invalid update request. User ID : " + user.getUserID() + " does not exist."); };

        userDAO.update(updatedUser);

    }

    @Override
    public void saveUser(User user) throws SQLException {
        if (user == null) { throw new IllegalArgumentException("User cannot be null."); }
        else if (user.getUserID() != null) { throw new IllegalArgumentException("Invalid creation request. User ID : " + user.getUserID() + " already exists."); }
        userDAO.save(user);
    }

    @Override
    public void deleteUser(int userID) throws SQLException {
        if (!userExists(userID)) { throw new ResourceDoesNotExistException("Invalid deletion request. User ID : " + userID + " does not exist."); }
        userDAO.delete(userID);
    }

    @Override
    public boolean isSameUser(User currentUser, User updatedUser) { // Determines if both user objects share the same read-only data uniquely identifying the same user
        return ( currentUser.getUserID() == updatedUser.getUserID() && currentUser.getRegistrationDate() == updatedUser.getRegistrationDate() && currentUser.getAccountType() == updatedUser.getAccountType() && currentUser.getDisplayName() == updatedUser.getDisplayName());
    }

    private boolean userExists(int userID) throws SQLException {
        return userDAO.get(userID) != null;
    }

}
