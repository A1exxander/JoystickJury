package xyz.joystickjury.backend.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static Connection databaseConnection;

    public static Connection getConnection() {

        if (databaseConnection == null){
            try {
                final String server = "jdbc:mysql://127.0.0.1:3306/joystickjury";
                final String username = System.getenv("JJ_DB_USERNAME");
                final String password = System.getenv("JJ_DB_PASSWORD");
                databaseConnection = DriverManager.getConnection(server, username, password);
            }
            catch (SQLException e){ // Not ideal but need to catch it to make it work else DAO classes will throw an exception saying that we cannot find connection bean when using Autowired
                e.printStackTrace();
            }
        }

        return databaseConnection;

    }

}
