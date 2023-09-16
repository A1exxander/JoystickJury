package user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.UserDAO;
import xyz.joystickjury.backend.user.UserType;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest { // Unit testing DAOs not using a JPA is not really worth it. Better to just integration test - ONLY RUN TESTS IN TESTING ENVIRONMENT

    UserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException {
        userDAO = new UserDAO();
    }

    @AfterEach
    void tearDown() {
        userDAO = null;
    }

    @Nested
    class getUserTest {

        @Test
        void should_GetCorrectUser_When_RequestingExistingUser() throws SQLException {
            User user = userDAO.get(1);
            assertEquals("raposoalexander@gmail.com", user.getEmail());
        }
        @Test
        void should_ReturnNull_When_RequestingNonExistentUser() throws SQLException {
            assertNull(userDAO.get(-1));
        }

    };

    @Nested
    class getAllTest {

        @Test
        void should_GetAll_When_Called() throws SQLException {
            assertTrue(userDAO.getAll().size() >= 1); // We will always have our UID # 1 account in our DB, so if we are returning a list greater than 1, it is valid
        }

    };

    @Nested
    class getByEmailTest {

        @Test
        void should_ReturnUID1_When_ReceivingRaposoAlexanderEmail() throws SQLException {
            assertEquals(1, userDAO.get("raposoalexander@gmail.com").getUserID());
        }
        @Test
        void should_ReturnNull_When_ReceivingNonExistentEmailAddress() throws SQLException {
            assertNull(userDAO.get("L"));
        }

    };

    @Nested
    class saveUserTest { // Must make sure our email does not exist. Ideally, run this test only once after setup and erase our newly created user after

        @Test
        void should_CreateUser_When_SavingNewUser() throws SQLException {
            try {
                User newUser = new User(null, "test@test.com", null, null, new Date(), UserType.ADMIN);
                userDAO.save(newUser);
            } catch (SQLIntegrityConstraintViolationException e){
                System.err.println("Test user already exists. Skipping creation...");
            }
            assertNotNull(userDAO.get("test@test.com"));
        }
        @Test
        void should_ThrowException_When_SavingExistingUser(){
            User newUser = new User(null, "test@test.com", null, null, new Date(), UserType.ADMIN);
            assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
                userDAO.save(newUser);
            });
        }

    };

    @Nested
    class deleteUserTest {

        @Test
        void should_DeleteUser_When_DeletingExistingUser() throws SQLException {
            User testUser = userDAO.get("test@test.com");
            userDAO.delete(testUser.getUserID());
            assertNull(userDAO.get(testUser.getUserID()));
        }
        @Test
        void should_ThrowException_When_DeletingNonExistentUser() {
            assertThrows(NullPointerException.class, () -> {
                userDAO.delete(null);
            });
        }
    };




}