package xyz.joystickjury.backend.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserServiceTest {

    @Mock
    UserDAO userDAOMock;

    @InjectMocks
    UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown(){
        userService = null;
        userDAOMock = null;
    }

    @Nested
    public class getUserTest {

        @Test
        public void getUser_shouldReturnCorrectUser_WhenUserExists() throws SQLException {

            User user = new User(0, "test@test.com", null, null, new Date(), AcccountType.ADMIN);
            when(userDAOMock.get(0)).thenReturn(user);
            User result = userService.getUser(0);
            Assertions.assertEquals(user, result);

        }

        @Test
        public void getUser_shouldReturnNull_WhenUserDoesNotExist() throws SQLException {

            when(userDAOMock.get(0)).thenReturn(null);
            User result = userService.getUser(0);
            assertNull(result);

        }

    }

    @Nested
    public class getAllUsersTest {

        @Test
        public void getAllUsers_shouldReturnAllUsers_WhenUsersExists() throws SQLException {

            List<User> actual = new ArrayList<>();

            User userOne = new User(0, "testOne@test.com", null, null, new Date(), AcccountType.ADMIN);
            User userTwo = new User(1, "testTwo@test.com", null, null, new Date(), AcccountType.ADMIN);
            actual.add(userOne);
            actual.add(userTwo);

            when(userDAOMock.getAll()).thenReturn(new ArrayList<User>(Arrays.asList(userOne, userTwo)));
            List<User> result = userService.getAllUsers();
            Assertions.assertEquals(actual, result);

        }

        @Test
        public void getAllUsers_shouldReturnEmptyList_WhenNoUsersExists() throws SQLException {

            when(userDAOMock.getAll()).thenReturn(new ArrayList<User>());
            List<User> result = userService.getAllUsers();
            assertEquals(0, result.size());

        }

    }

    @Nested
    public class createUserTest {

        @Test
        public void createUser_shouldCreateUser_whenCreatingNewValidUser() throws SQLException {

            User newUser = new User(0, "test@test.com", null, null, new Date(), AcccountType.ADMIN);
            userService.saveUser(newUser);
            verify(userDAOMock).save(newUser);

        }

    }

    @Nested
    public class deleteUserTest {

        @Test
        public void deleteUser_shouldDeleteUser_WhenUsersExists() throws SQLException {

            Integer userID = 0;
            userService.deleteUser(userID);
            verify(userDAOMock).delete(userID);

        }

    }

    @Nested
    public class updateUserTest {

        @Test
        public void updateUser_shouldUpdateUser_WhenValidRequest() throws SQLException {

            User currentUser = new User(1, "oldemail@test.com", null, null, new Date(), AcccountType.ADMIN);
            User updatedUser = new User(1, "newemail@test.com", null, null, currentUser.getRegistrationDate(), AcccountType.ADMIN);

            userService.updateUser(currentUser, updatedUser);
            verify(userDAOMock).update(updatedUser);

        }

        @Test
        public void updateUser_shouldThrowException_WhenNewEmailInvalid() throws SQLException {

            User currentUser = new User(1, "oldemail@test.com", null, null, new Date(), AcccountType.ADMIN);
            User updatedUser = new User(1, "newemailhuehuehue", null, null, currentUser.getRegistrationDate(), AcccountType.ADMIN);

            assertThrows(IllegalArgumentException.class, () -> {
                userService.updateUser(currentUser, updatedUser);
            });

        }

        @Test
        public void updateUser_shouldThrowException_WhenNoUserProvided() throws SQLException {

            User user = new User(1, "oldemail@test.com", null, null, new Date(), AcccountType.ADMIN);

            assertThrows(IllegalArgumentException.class, () -> {
                userService.updateUser(user, null);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                userService.updateUser(null, user);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                userService.updateUser(null, null);
            });

        }

    }



}
