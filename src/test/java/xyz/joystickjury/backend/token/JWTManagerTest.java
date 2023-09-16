package xyz.joystickjury.backend.token;

import io.fusionauth.jwt.domain.JWT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.joystickjury.backend.user.User;
import xyz.joystickjury.backend.user.AcccountType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JWTManagerTest {

    private JWTManager jwtManager;

    @BeforeEach
    public void setUp(){
        jwtManager = new JWTManager();
    }
    @AfterEach
    public void tearDown(){
        jwtManager = null;
    }

    @Nested
    public class generateJWTTest {

        @Test
        public void should_ReturnValidJWT_WhenValidGeneration() {

            User u = new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN);
            String generatedJWT = jwtManager.generateJWT(u);
            assertTrue(jwtManager.isValidJWT(generatedJWT));

        }

    }

    @Nested
    public class isValidJWTTest {

        @Test
        public void isValidJWT_ShouldReturnTrue_WhenJWTExists() {

            User u = new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN);
            String generatedJWT = jwtManager.generateJWT(u);
            assertTrue(jwtManager.isValidJWT(generatedJWT));

        }

        @Test
        public void isValidJWT_ShouldReturnFalse_WhenJWTNonExistent() { assertFalse(jwtManager.isValidJWT("")); }

        @Test
        public void isValidJWT_ShouldReturnFalse_WhenJWTInvalidated() {

            String generatedJWT = jwtManager.generateJWT(new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN));
            jwtManager.invalidateJWT(generatedJWT);
            assertFalse(jwtManager.isValidJWT(generatedJWT));

        }

        @Test
        public void isValidJWT_ShouldThrowException_WhenJWTNull() {

            assertThrows(IllegalArgumentException.class, () -> {
                jwtManager.isValidJWT(null);
            });

        }

    }

    @Nested
    public class jwtDecodeTest{

        @Test
        public void decodeJWT_ShouldReturnValidDecodedJWT_WhenCalled() {

            int expectedSubject = 0;
            String generatedJWT = jwtManager.generateJWT(new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN));
            JWT decodedJwt = jwtManager.decodeJWT(generatedJWT);
            int actualSubject = Integer.valueOf(decodedJwt.subject);
            assertEquals(expectedSubject, actualSubject);

        }

    }

    @Nested
    public class extractBearerJWTTest {

        @Test
        public void extractBearerJWT_ShouldReturnTest_WhenJWTEqualsBearer_test() {

            String jwt = "Bearer Test";
            String expectedExtracted = "Test";
            String result = jwtManager.extractBearerJWT(jwt);
            assertEquals(expectedExtracted, result);

        }

        @Test
        public void extractBearerJWT_ShouldThrowException_WhenJWTNull() {

            assertThrows(IllegalArgumentException.class, () -> {
                jwtManager.extractBearerJWT(null);
            });

        }

        @Test
        public void extractBearerJWT_ShouldThrowException_WhenDoesNotStartWithBearer() {

            assertThrows(IllegalArgumentException.class, () -> {
                jwtManager.extractBearerJWT("Test Test");
            });

        }

    }

}