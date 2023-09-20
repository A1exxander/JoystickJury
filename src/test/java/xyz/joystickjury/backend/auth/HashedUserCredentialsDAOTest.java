package xyz.joystickjury.backend.auth;

import org.junit.jupiter.api.*;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class HashedUserCredentialsDAOTest { // Unit testing DAOs not using a JPA is not really worth it. Better to just integration test - ONLY RUN TESTS IN TESTING ENVIRONMENT

    private static HashedUserCredentialsDAO mockedUserCredentialsDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        mockedUserCredentialsDAO = new HashedUserCredentialsDAO(DatabaseConnectionManager.getConnection());
    }

    @AfterAll
    public static void tearDown() {
        mockedUserCredentialsDAO = null;
    }

    @Nested
    public class getUserCredentialTest {

    }

}