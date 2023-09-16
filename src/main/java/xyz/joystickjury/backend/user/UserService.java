package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.joystickjury.backend.email.EmailAddressValidator;

import java.sql.SQLException;
import java.util.List;

@Service
@AllArgsConstructor @NoArgsConstructor
public class UserService implements iUserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public User getUser(Integer id) throws SQLException {
        return userDAO.get(id);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAll();
    }

    @Override
    public void updateUser(User user, User updatedUser) throws SQLException {

        EmailAddressValidator emailAddressValidator = new EmailAddressValidator();

        if (user == null || updatedUser == null) { throw new IllegalArgumentException(); }
        else if (!emailAddressValidator.isValidEmailAddress(updatedUser.getEmail())) { throw new IllegalArgumentException("Invalid update request. New email is invalid."); }

        userDAO.update(updatedUser);

    }

    @Override
    public void saveUser(User user) throws SQLException {
        if (user == null) { throw new IllegalArgumentException(); }
        userDAO.save(user);
    }

    @Override
    public void deleteUser(Integer id) throws SQLException {
        userDAO.delete(id);
    }

    @Override
    public boolean isSameUser(User currentUser, User updatedUser) { // Determines if both user objects share the same read-only data uniquely identifying the same user
        return ( currentUser.getUserID() == updatedUser.getUserID() && currentUser.getRegistrationDate() == updatedUser.getRegistrationDate() && currentUser.getAccountType() == updatedUser.getAccountType());
    }

}
