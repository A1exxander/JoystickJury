package xyz.joystickjury.backend.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;

import javax.validation.constraints.Min;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


@Repository
@AllArgsConstructor @NoArgsConstructor
public class UserDAO implements iUserDAO { // In future projects, implement your DB w Hibernate - Makes it much simpler & easier to implement and unit test with DBUnit

    private Connection databaseConnection = DatabaseConnectionManager.getConnection();

    @Override
    public User get(Integer id) throws SQLException {

        final String query = "SELECT * FROM User WHERE UserID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            return new User(results.getInt("UserID"), // Ideally switch to Mapper
                    results.getString("DisplayName"),
                    results.getString("ProfilePictureLink"),
                    results.getString("ProfileDescription"),
                    results.getDate("RegistrationDate"),
                    AcccountType.valueOf(results.getString("AccountType")));
        }
        else {
            return null;
        }

    }

    @Override
    public User get(String displayName) throws SQLException {

        final String query = "SELECT * FROM User WHERE DisplayName = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setString(1, displayName);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            return new User(results.getInt("UserID"), // Ideally switch to Mapper
                    results.getString("DisplayName"),
                    results.getString("ProfilePictureLink"),
                    results.getString("ProfileDescription"),
                    results.getDate("RegistrationDate"),
                    AcccountType.valueOf(results.getString("AccountType")));
        }
        else {
            return null;
        }

    }

    @Override
    public List<User> getAll() throws SQLException {

        final String query = "SELECT * FROM User";
        List<User> users = new LinkedList<User>();
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            users.add(new User(results.getInt("UserID"),
                    results.getString("DisplayName"),
                    results.getString("ProfilePictureLink"),
                    results.getString("ProfileDescription"),
                    results.getDate("RegistrationDate"),
                    AcccountType.valueOf(results.getString("AccountType"))));
        }

        return users;

    }

    @Override
    public void save(User user) throws SQLException { // Somewhat violates the SRP. Can refactor to use a UserDAOStatementFactory class but that's too much voodoo

        final String query = "INSERT INTO User (DisplayName, ProfilePictureLink, ProfileDescription, RegistrationDate, AccountType) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

        preparedStatement.setString(1, user.getDisplayName());
        preparedStatement.setString(2, user.getProfilePictureLink());
        preparedStatement.setString(3, user.getProfileDescription());
        preparedStatement.setDate(4, new java.sql.Date(user.getRegistrationDate().getTime()));
        preparedStatement.setString(5, user.getAccountType().toString());

        preparedStatement.executeUpdate();

    }

    @Override
    public void update(User user) throws SQLException {

        final String query = "UPDATE User SET DisplayName=?, ProfilePictureLink=?, ProfileDescription=?, RegistrationDate=?, AccountType=? WHERE UserID=?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

        preparedStatement.setString(1, user.getDisplayName());
        preparedStatement.setString(2, user.getProfilePictureLink());
        preparedStatement.setString(3, user.getProfileDescription());
        preparedStatement.setDate(4, new java.sql.Date(user.getRegistrationDate().getTime()));
        preparedStatement.setString(5, user.getAccountType().toString());
        preparedStatement.setInt(6, user.getUserID()); // Assuming the user object has a userID field

        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        final String query = "DELETE FROM User WHERE UserID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();

    }

}
