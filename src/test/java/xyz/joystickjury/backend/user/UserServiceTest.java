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
import xyz.joystickjury.backend.cexceptions.UnauthorizedOperationException;
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

            User user = new User();
            user.setUserID(1);
            user.setRegistrationDate(new Date());
            user.setAccountType(UserType.ADMIN);

            when(userDAOMock.get(1)).thenReturn(user);
            User result = userService.getUser(1);
            Assertions.assertEquals(user, result);

        }

        @Test
        public void getUser_shouldReturnNull_WhenUserDoesNotExist() throws SQLException {

            when(userDAOMock.get(1)).thenReturn(null);
            User result = userService.getUser(1);
            assertNull(result);

        }

    }

    @Nested
    public class getAllUsersTest {

        @Test
        public void getAllUsers_shouldReturnAllUsers_WhenUsersExists() throws SQLException {

            List<User> actual = new ArrayList<>();

            User userOne = new User();
            userOne.setUserID(1);
            actual.add(userOne);

            User userTwo = new User();
            userTwo.setUserID(2);
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
        public void deleteUser_shouldDeleteUser_WhenUsersExists() throws SQLException {

            User newUser = new User();
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

            User currentUser = new User(1, "oldemail@test.com", null, null, null, UserType.ADMIN);
            User updatedUser = new User(1, "newemail@test.com", null, null, null, UserType.ADMIN);

            userService.updateUser(currentUser, updatedUser);
            verify(userDAOMock).update(updatedUser);

        }

        @Test
        public void updateUser_shouldThrowException_WhenNewEmailInvalid() throws SQLException {

            User currentUser = new User(1, "oldemail@test.com", null, null, null, UserType.ADMIN);
            User updatedUser = new User(1, "invalidemailhuehuehue", null, null, null, UserType.ADMIN);

            assertThrows(IllegalArgumentException.class, () -> {
                userService.updateUser(currentUser, updatedUser);
            });

        }

        @Test
        public void updateUser_shouldThrowException_WhenNoUserProvided() throws SQLException {

            User user = new User(null, null, null, null, null, null);

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

        @Test
        public void updateUser_shouldThrowException_WhenUpdatingReadOnlyData() throws SQLException {

            User currentUser = new User(1, "test@test.com", null, null, null, UserType.ADMIN);
            User updatedUser = new User(2, "test@test.com", null, null, null, UserType.REVIEWER);

            assertThrows(UnauthorizedOperationException.class, () -> {
                userService.updateUser(currentUser, updatedUser);
            });

        }



    }



}
