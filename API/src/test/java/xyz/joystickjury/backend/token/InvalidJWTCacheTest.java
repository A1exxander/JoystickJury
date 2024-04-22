package xyz.joystickjury.backend.token;

import io.fusionauth.jwt.domain.JWT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;


class InvalidJWTCacheTest {

    private InvalidJWTCache invalidJWTCache;
    private JWT JWTMock;

    @BeforeEach
    void setUp() {
        invalidJWTCache = new InvalidJWTCache();
        JWTMock = Mockito.mock(JWT.class);
    }

    @AfterEach
    void tearDown() {
        invalidJWTCache = null;
        JWTMock = null;
    }

    @Nested
    class AddTokenTest {

        @Test
        public void shouldAddToken_whenJWTValid() {

            Mockito.when(JWTMock.isExpired()).thenReturn(false);
            invalidJWTCache.addToken(JWTMock);
            assertTrue(invalidJWTCache.containsToken(JWTMock));

        }

        @Test
        public void shouldNotAddJWT_whenJWTExpired() {

            Mockito.when(JWTMock.isExpired()).thenReturn(true);
            invalidJWTCache.addToken(JWTMock);
            assertFalse(invalidJWTCache.containsToken(JWTMock));

        }

    }

    @Nested
    class RemoveTokenTest {

        @Test
        public void shouldRemoveJWT_whenJWTExists() {

            Mockito.when(JWTMock.isExpired()).thenReturn(false);
            invalidJWTCache.addToken(JWTMock);
            invalidJWTCache.removeToken(JWTMock);
            assertFalse(invalidJWTCache.containsToken(JWTMock));

        }

    }

    @Nested
    class ContainTokenTest { // Method is already tested w the other tests

        @Test
        public void shouldNotContainJWT_whenJWTNotAdded() { // The stupidest test ever
            assertFalse(invalidJWTCache.containsToken(JWTMock));
        }

    }

    @Nested
    class RemoveExpiredJWTsTest{

        @Test
        public void shouldNotContainExpiredJWTs_afterRemovingExpiredJWTs() {

            JWT expiringJWTMock= Mockito.mock(JWT.class);
            Mockito.when(JWTMock.isExpired()).thenReturn(false);
            Mockito.when(expiringJWTMock.isExpired()).thenReturn(false);

            invalidJWTCache.addToken(JWTMock);
            invalidJWTCache.addToken(expiringJWTMock);

            Mockito.when(expiringJWTMock.isExpired()).thenReturn(true); // Make the first token expire

            try {
                Method removeExpiredJWTMethod = InvalidJWTCache.class.getDeclaredMethod("removeExpiredJWTs");
                removeExpiredJWTMethod.setAccessible(true);
                removeExpiredJWTMethod.invoke(invalidJWTCache);
            } catch (Exception e) {
                fail();
            }

            assertAll(()->{
                assertTrue(invalidJWTCache.containsToken(JWTMock));
                assertFalse(invalidJWTCache.containsToken(expiringJWTMock));
            });

        }

    }

}