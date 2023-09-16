package xyz.joystickjury.backend.token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.joystickjury.backend.token.JWTManager;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.UserType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JWTManagerTest {

    private JWTManager JWTManager;

    @BeforeEach
    public void setUp(){
        JWTManager = new JWTManager();
    }
    @AfterEach
    public void tearDown(){
        JWTManager = null;
    }

    @Nested
    public class generateTokenTest {
        @Test
        public void should_ReturnValidToken_When_ValidGeneration(){
            User u = new User(1,"johnsmith@gmail.com", null,null, new Date(), UserType.ADMIN);
            String generatedJWT = JWTManager.generateJWT(u);
            assertTrue(JWTManager.isValidJWT(generatedJWT));
        }
    }
}