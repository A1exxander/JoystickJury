package xyz.joystickjury.backend.auth;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


@Repository
public class UserCredentialsDAO implements iUserCredentialsDAO {

    private final Connection databaseConnection = DatabaseConnectionManager.getConnection();

    @Override
    public UserCredentials get(Integer id) throws SQLException {

        final String query = "SELECT * FROM UserCredential WHERE UserCredentialID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            return new UserCredentials(results.getInt("UserID"), results.getString("Email"), results.getString("HashedPassword"));
        }
        else {
            return null;
        }

    }

    @Override
    public UserCredentials getByUserID(Integer id) throws SQLException {

        final String query = "SELECT * FROM UserCredential WHERE UserID = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            return new UserCredentials(results.getInt("UserID"), results.getString("Email"), results.getString("HashedPassword"));
        }
        else {
            return null;
        }

    }

    @Override
    public UserCredentials get(String email) throws SQLException {

        final String query = "SELECT * FROM UserCredential WHERE Email = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet results = statement.executeQuery();

        if (results.next()){
            return new UserCredentials(results.getInt("UserID"), results.getString("Email"), results.getString("HashedPassword"));
        }
        else {
            return null;
        }

    }

    @Override
    public List<UserCredentials> getAll() throws SQLException {

        final String query = "SELECT * FROM UserCredential";
        List<UserCredentials> hashedUserCredentials = new LinkedList<UserCredentials>();
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            hashedUserCredentials.add(new UserCredentials(results.getInt("UserID"), results.getString("Email"), results.getString("HashedPassword")));
        }

        return hashedUserCredentials;

    }

    @Override
    public void save(UserCredentials hashedUserCredential) throws SQLException {

        final String query = "INSERT INTO UserCredential(UserID, Email, HashedPassword) VALUES(?, ?, ?)";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, hashedUserCredential.getUserID());
        statement.setString(2, hashedUserCredential.getEmail());
        statement.setString(3, hashedUserCredential.getPassword());

        statement.executeUpdate();

    }

    @Override
    public void update(UserCredentials hashedUserCredential) throws SQLException {

        final String query = "UPDATE UserCredential SET Email = ? HashedPassword = ? WHERE UserID = ?";

        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setString(1, hashedUserCredential.getEmail());
        statement.setString(2, hashedUserCredential.getPassword());
        statement.setInt(3, hashedUserCredential.getUserID());

        statement.executeUpdate();

    }

    @Override
    public void delete(Integer id) throws SQLException {

        final String query = "DELETE FROM User WHERE UserCredentialID  = ?";
        PreparedStatement statement = databaseConnection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();

    }

}
