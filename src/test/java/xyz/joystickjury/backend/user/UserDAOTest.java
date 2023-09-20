package xyz.joystickjury.backend.user;

import org.junit.jupiter.api.*;
import xyz.joystickjury.backend.utils.DatabaseConnectionManager;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest { // Unit testing DAOs not using a JPA is not really worth it. Better to just integration test - ONLY RUN TESTS IN TESTING ENVIRONMENT

    private static UserDAO mockedUserDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        mockedUserDAO = new UserDAO(DatabaseConnectionManager.getConnection());
    }

    @AfterAll
    public static void tearDown() {
        mockedUserDAO = null;
    }

    @Nested
    public class getUserTest {

        @Test
        public void should_GetCorrectUser_When_RequestingExistingUser() throws SQLException {
            User user = mockedUserDAO.get(1);
            assertEquals("raposoalexander", user.getDisplayName());
        }
        @Test
        public void should_ReturnNull_When_RequestingNonExistentUser() throws SQLException {
            assertNull(mockedUserDAO.get(-1));
        }

    };

    @Nested
    public class getAllTest {

        @Test
        public void should_GetAll_When_Called() throws SQLException {
            assertTrue(mockedUserDAO.getAll().size() >= 1); // We will always have our UID # 1 account in our DB, so if we are returning a list greater than 1, it is valid
        }

    };

    @Nested
    public class getByEmailTest {

        @Test
        public void should_ReturnUID1_When_ReceivingRaposoAlexanderEmail() throws SQLException {
            assertEquals(1, mockedUserDAO.get("raposoalexander").getUserID());
        }
        @Test
        public void should_ReturnNull_When_ReceivingNonExistentEmailAddress() throws SQLException {
            assertNull(mockedUserDAO.get("L"));
        }

    };

    @Nested
    public class saveUserTest { // Must make sure our email does not exist. Ideally, run this test only once after setup and erase our newly created user after

        @Test
        public void should_CreateUser_When_SavingNewUser() throws SQLException {

            try {
                User newUser = new User(null, "test", null, null, new Date(), AcccountType.ADMIN);
                mockedUserDAO.save(newUser);
            } catch (SQLIntegrityConstraintViolationException e){
                System.err.println("Test user already exists. Skipping creation...");
            }

            assertNotNull(mockedUserDAO.get("test"));

        }
        @Test
        public void should_ThrowException_When_SavingExistingUser(){

            User newUser = new User(null, "test", null, null, new Date(), AcccountType.ADMIN);
            assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
                mockedUserDAO.save(newUser);
            });

        }

    };

    @Nested
    public class deleteUserTest {

        @Test
        public void should_DeleteUser_When_DeletingExistingUser() throws SQLException {

            User testUser = mockedUserDAO.get("test");
            mockedUserDAO.delete(testUser.getUserID());
            assertNull(mockedUserDAO.get(testUser.getUserID()));

        }

        @Test
        public void should_ThrowException_When_DeletingNonExistentUser() {

            assertThrows(NullPointerException.class, () -> {
                mockedUserDAO.delete(null);
            });

        }
    };

}