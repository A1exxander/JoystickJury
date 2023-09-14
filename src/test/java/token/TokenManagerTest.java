package token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import user.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenManagerTest {

    private TokenManager tokenManager;

    @BeforeEach
    void setUp(){
        tokenManager = new TokenManager();
    }
    @AfterEach
    void tearDown(){
        tokenManager = null;
    }

    @Nested
    public class generateTokenTest {
        @Test
        void should_ReturnValidToken_When_ValidGeneration(){
            User u = new User(1,"johnsmith@gmail.com", null,null, new Date(), UserType.ADMIN);
            String generatedToken = tokenManager.generateToken(u);
            assertTrue(tokenManager.isValidToken(generatedToken));
        }
    }
}