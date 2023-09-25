package xyz.joystickjury.backend.auth;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.joystickjury.backend.exception.EmailMismatchException;
import xyz.joystickjury.backend.exception.ResourceDoesNotExistException;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCredentialsServiceTest {

    private UserCredentialsDAO userCredentialsDAOMock;
    private UserCredentialsService userCredentialsService;

    @BeforeEach
    void setUp() {
        userCredentialsDAOMock = mock(UserCredentialsDAO.class);
        userCredentialsService = new UserCredentialsService(userCredentialsDAOMock);
    }

    @AfterEach
    void tearDown() {
        userCredentialsDAOMock = null;
        userCredentialsService = null;
    }

    @Nested
    public class getHashedUserCredentialsTest {

        @Test
        public void getHashedUserCredentials_ShouldReturnValidCredentials_WhenEmailValid() throws SQLException {

            UserCredentials expectedCredentials = new UserCredentials(1, "test@test.com", "password");
            when(userCredentialsDAOMock.get("test@test.com")).thenReturn(expectedCredentials);

            UserCredentials actualCredentials = userCredentialsService.getHashedUserCredentials("test@test.com");

            assertNotNull(actualCredentials);
            assertEquals(expectedCredentials, actualCredentials);

        }

        @Test
        public void getHashedUserCredentials_ShouldThrowException_WhenEmailNULL() throws SQLException {

            assertThrows(ResourceDoesNotExistException.class, () -> {
                userCredentialsService.getHashedUserCredentials(null);
            });

        }

        @Test
        public void getHashedUserCredentials_ShouldThrowException_WhenEmailInvalid() throws SQLException {

            when(userCredentialsDAOMock.get("thisemaildoesntexist@reallyitdoesnt.com")).thenReturn(null);

            assertThrows(ResourceDoesNotExistException.class, () -> {
                userCredentialsService.getHashedUserCredentials("thisemaildoesntexist@reallyitdoesnt.com");
            });

        }

    }

    @Nested
    public class createHashedUserCredentialsTest {

        @Test
        public void createHashedUserCredentials_ShouldReturnValidHashedCredentials_WhenCredentialsValid() throws SQLException {

            UserCredentials rawCredentials = new UserCredentials(1, "test@test.com", "password");
            UserCredentials hashedCredentials = userCredentialsService.createHashedUserCredentials(rawCredentials);

            assertEquals(rawCredentials.getUserID(), hashedCredentials.getUserID());
            assertEquals(rawCredentials.getEmail(), hashedCredentials.getEmail());
            assertTrue(userCredentialsService.areValidCredentials(rawCredentials, hashedCredentials));

        }

        @Test
        public void createHashedUserCredentials_ShouldThrowException_WhenCredentialsNULL() throws SQLException {

            assertThrows(NullPointerException.class, () -> {
                userCredentialsService.createHashedUserCredentials(null);
            });

        }

    }

    @Nested
    public class saveCredentialsTest {

        @Test
        public void saveCredentials_shouldCallDAOMethod_WhenCredentialsValid() throws SQLException {

            UserCredentials newUserCredentials = new UserCredentials(1, "test@test.com", "password");
            userCredentialsService.saveCredentials(newUserCredentials);
            Mockito.verify(userCredentialsDAOMock).save(newUserCredentials);

        }

        @Test
        public void saveCredentials_shouldThrowException_WhenCredentialsNULL() throws SQLException {

            assertThrows(NullPointerException.class, () -> {
                userCredentialsService.saveCredentials(null);
            });

        }

        @Test
        public void saveCredentials_shouldThrowException_WhenCredentialsIDNull() throws SQLException {

            UserCredentials newUserCredentials = new UserCredentials(null, "test@test.com", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                userCredentialsService.saveCredentials(newUserCredentials);
            });

        }

    }

    @Nested
    public class emailExistsTest {

        @Test
        public void emailExists_ShouldReturnTrue_WhenEmailExists() throws SQLException {

            when(userCredentialsDAOMock.get("existing@example.com")).thenReturn(new UserCredentials(1, "existing@example.com", "password"));
            boolean result = userCredentialsService.emailExists("existing@example.com");
            assertTrue(result);

        }

        @Test
        public void emailExists_ShouldReturnFalse_WhenEmailDoesNotExist() throws SQLException {
            when(userCredentialsDAOMock.get("existing@example.com")).thenReturn(null);
            boolean result = userCredentialsService.emailExists("existing@example.com");
            assertFalse(result);
        }

    }

    @Nested
    public class areValidCredentialsTest {

        @Test
        public void areValidCredentials_ShouldReturnTrue_WhenCredentialsAreValid() {

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserCredentials userCredentials = new UserCredentials(1, "test@test.com", "password");
            UserCredentials hashedUserCredentials = new UserCredentials(1, "test@test.com", passwordEncoder.encode(userCredentials.getPassword()));

            boolean result = userCredentialsService.areValidCredentials(userCredentials, hashedUserCredentials);

            assertTrue(result);

        }

        @Test
        public void areValidCredentials_ShouldThrowEmailMismatchException_WhenEmailsDoNotMatch() {

            UserCredentials userCredentials = new UserCredentials(1, "test@test.com", "password");
            UserCredentials hashedUserCredentials = new UserCredentials(1, "different@example.com", "PASSWORDDONTMATTER");

            EmailMismatchException exception = assertThrows(EmailMismatchException.class, () -> {
                userCredentialsService.areValidCredentials(userCredentials, hashedUserCredentials);
            });

        }

        @Test
        public void areValidCredentials_ShouldReturnFalse_WhenPasswordsDoNotMatch() {

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            UserCredentials userCredentials = new UserCredentials(1, "test@example.com", "password");
            UserCredentials hashedUserCredentials = new UserCredentials(1, "test@example.com", passwordEncoder.encode("wrongpasswordlul"));

            boolean result = userCredentialsService.areValidCredentials(userCredentials, hashedUserCredentials);

            assertFalse(result);

        }

        @Test
        public void areValidCredentials_ShouldThrowException_WhenCredentialsNull() {

            UserCredentials userCredentials = new UserCredentials(1, "test@example.com", "password");
            UserCredentials hashedUserCredentials = null;

            assertThrows(NullPointerException.class, () -> {
                userCredentialsService.areValidCredentials(userCredentials, hashedUserCredentials);
            });

        }

    }

    @Nested
    public class updateUserCredentialsTest{
        @Test
        public void updateUserCredentials_ShouldUpdateCredentials_WhenValidRequest() throws SQLException, EmailMismatchException {

            UserCredentials hashedUserCredentials = new UserCredentials(1, "test@example.com", "$2a$12$JtK1KZvmrDL7fXAvhefKtufcV6Cb3Uf1OqkV71R50JByyQRFsVtuG");
            when(userCredentialsDAOMock.getByUserID(hashedUserCredentials.getUserID())).thenReturn(hashedUserCredentials);

            userCredentialsService.updateUserCredentials(hashedUserCredentials);

            verify(userCredentialsDAOMock).update(hashedUserCredentials);

        }

        @Test
        public void updateUserCredentials_ShouldThrowIllegalArgumentException_WhenUserIDIsNull() {

            UserCredentials hashedUserCredentials = new UserCredentials(null, "test@example.com", "$2a$12$JtK1KZvmrDL7fXAvhefKtufcV6Cb3Uf1OqkV71R50JByyQRFsVtuG");

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                userCredentialsService.updateUserCredentials(hashedUserCredentials);
            });

        }

        @Test
        public void updateUserCredentials_ShouldThrowResourceDoesNotExistException_WhenUserIDDoesNotExist() throws SQLException {

            UserCredentials hashedUserCredentials = new UserCredentials(1, "test@example.com", "$2a$12$JtK1KZvmrDL7fXAvhefKtufcV6Cb3Uf1OqkV71R50JByyQRFsVtuG");
            when(userCredentialsDAOMock.getByUserID(hashedUserCredentials.getUserID())).thenReturn(null);

            ResourceDoesNotExistException exception = assertThrows(ResourceDoesNotExistException.class, () -> {
                userCredentialsService.updateUserCredentials(hashedUserCredentials);
            });

        }
    }

}

    /*
    public void updateUserCredentials(@NotNull UserCredentials hashedUserCredentials) throws EmailMismatchException, SQLException;
     */