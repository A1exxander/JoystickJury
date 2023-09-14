package utils;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnectionManager {

    private static Connection databaseConnection;

    public static Connection getConnection() throws SQLException {

        if (databaseConnection == null){
            final String server = "jdbc:mysql://127.0.0.1:3306/joystickjury";
            final String username = System.getenv("JJ_DB_USERNAME");
            final String password = System.getenv("JJ_DB_PASSWORD");
            databaseConnection = DriverManager.getConnection(server, username, password);
        }

        return databaseConnection;

    }

}
