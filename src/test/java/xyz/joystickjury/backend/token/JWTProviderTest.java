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

public class JWTProviderTest {

    private JWTProvider jwtProvider;

    @BeforeEach
    public void setUp(){
        jwtProvider = new JWTProvider();
    }
    @AfterEach
    public void tearDown(){
        jwtProvider = null;
    }

    @Nested
    public class generateJWTTest {

        @Test
        public void should_ReturnValidJWT_WhenValidGeneration() {

            User u = new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN);
            String generatedJWT = jwtProvider.generateJWT(u);
            assertTrue(jwtProvider.isValidJWT(generatedJWT));

        }

    }

    @Nested
    public class isValidJWTTest {

        @Test
        public void isValidJWT_ShouldReturnTrue_WhenJWTExists() {

            User u = new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN);
            String generatedJWT = jwtProvider.generateJWT(u);
            assertTrue(jwtProvider.isValidJWT(generatedJWT));

        }

        @Test
        public void isValidJWT_ShouldReturnFalse_WhenJWTNonExistent() { assertFalse(jwtProvider.isValidJWT("")); }

        @Test
        public void isValidJWT_ShouldReturnFalse_WhenJWTInvalidated() {

            String generatedJWT = jwtProvider.generateJWT(new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN));
            jwtProvider.invalidateJWT(generatedJWT);
            assertFalse(jwtProvider.isValidJWT(generatedJWT));

        }

        @Test
        public void isValidJWT_ShouldThrowException_WhenJWTNull() {

            assertThrows(IllegalArgumentException.class, () -> {
                jwtProvider.isValidJWT(null);
            });

        }

    }

    @Nested
    public class jwtDecodeTest{

        @Test
        public void decodeJWT_ShouldReturnValidDecodedJWT_WhenCalled() {

            int expectedSubject = 0;
            String generatedJWT = jwtProvider.generateJWT(new User(0,"test@test.com", null,null, new Date(), AcccountType.ADMIN));
            JWT decodedJwt = jwtProvider.decodeJWT(generatedJWT);
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
            String result = jwtProvider.extractBearerJWT(jwt);
            assertEquals(expectedExtracted, result);

        }

        @Test
        public void extractBearerJWT_ShouldThrowException_WhenJWTNull() {

            assertThrows(IllegalArgumentException.class, () -> {
                jwtProvider.extractBearerJWT(null);
            });

        }

        @Test
        public void extractBearerJWT_ShouldThrowException_WhenDoesNotStartWithBearer() {

            assertThrows(IllegalArgumentException.class, () -> {
                jwtProvider.extractBearerJWT("Test Test");
            });

        }

    }

}