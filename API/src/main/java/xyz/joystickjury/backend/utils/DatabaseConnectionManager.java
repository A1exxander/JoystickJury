package xyz.joystickjury.backend.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static Connection databaseConnection;

    public static Connection getConnection() {

        if (databaseConnection == null){
            try {
                final String server = System.getenv("MYSQL_SERVER") + "joystickjury";
                final String username = System.getenv("MYSQL_ROOT_USER");
                final String password = System.getenv("MYSQL_ROOT_PASSWORD");
                databaseConnection = DriverManager.getConnection(server, username, password);
            }
            catch (SQLException e){ // Not ideal but need to catch it to make it work else DAO classes will throw an exception saying that we cannot find connection bean when using Autowired
                e.printStackTrace();
            }
        }

        return databaseConnection;

    }

}
