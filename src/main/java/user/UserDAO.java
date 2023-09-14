package user;

import lombok.AllArgsConstructor;
import net.sf.resultsetmapper.ReflectionResultSetMapper;
import net.sf.resultsetmapper.ResultSetMapper;
import org.springframework.stereotype.Repository;
import utils.DatabaseConnectionManager;
import utils.iDAO;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDAO implements iDAO<User, Integer> { // In future projects, implement your DB w Hibernate - Makes it much simpler & easier to implement and unit test with DBUnit

    Connection databaseConnection;
    ResultSetMapper<User> resultSetMapper = new ReflectionResultSetMapper<User>(User.class);

    public UserDAO() throws SQLException { // Cannot use @NoArgsConstructor w default values assigned to members as setting databaseConnection member with DriverManager can throw an exception
        databaseConnection = DatabaseConnectionManager.getConnection(); // Singleton instance to our DB to prevent multiple instances
    }

    @Override
    public User get(Integer id) throws SQLException {

        final String query = "SELECT * FROM Users WHERE ID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();

        return results.next() ? resultSetMapper.mapRow(results) : null;

    }

    @Override
    public List<User> getAll() throws SQLException {

        final String query = "SELECT * FROM Users";
        List<User> users = new LinkedList<User>();
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            users.add(resultSetMapper.mapRow(results));
        }

        return users;

    }

    @Override
    public void save(User user) throws SQLException { // Somewhat violates the SRP. Can refactor to use a UserDAOStatementFactory class but that's too much voodoo

        final String query = "INSERT INTO Users (Email, ProfilePictureLink, ProfileDescription, RegistrationDate, AccountType) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getProfilePictureLink());
        preparedStatement.setString(3, user.getProfileDescription());
        preparedStatement.setDate(4, new java.sql.Date(user.getRegistrationDate().getTime()));
        preparedStatement.setString(5, user.getUserType().toString());

        preparedStatement.executeUpdate();

    }

    @Override
    public void update(User user) throws SQLException {

        final String query = "UPDATE Users SET Email=?, ProfilePictureLink=?, ProfileDescription=?, RegistrationDate=?, AccountType=? WHERE UserID=?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getProfilePictureLink());
        preparedStatement.setString(3, user.getProfileDescription());
        preparedStatement.setDate(4, new java.sql.Date(user.getRegistrationDate().getTime()));
        preparedStatement.setString(5, user.getUserType().toString());
        preparedStatement.setInt(6, user.getUserID()); // Assuming the user object has a userID field

        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        final String query = "DELETE FROM Users WHERE ID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();

    }
}
