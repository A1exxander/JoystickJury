package xyz.joystickjury.backend.auth;

import org.junit.jupiter.api.*;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;

import java.sql.SQLException;

class HashedUserCredentialsDAOTest { // Unit testing DAOs not using a JPA is not really worth it. Better to just integration test - ONLY RUN TESTS IN TESTING ENVIRONMENT

    private static UserCredentialsDAO mockedUserCredentialsDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        mockedUserCredentialsDAO = new UserCredentialsDAO();
    }

    @AfterAll
    public static void tearDown() {
        mockedUserCredentialsDAO = null;
    }

    @Nested
    public class getUserCredentialTest {

    }

}