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
            final String serverEndpoint = new StringBuilder("jdbc:oracle:thin:" + System.getenv("DB_USERNAME") + "/" + System.getenv("DB_PASSWORD") + System.getenv("DB_SERVER")).toString();
            databaseConnection = DriverManager.getConnection(serverEndpoint);
        }
        return databaseConnection;
    }

}
