package xyz.joystickjury.backend.email;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.joystickjury.backend.email.EmailAddressValidator;

class EmailAddressValidatorTest {

    EmailAddressValidator emailAddressValidator;

    @BeforeEach
    void setup() {
        emailAddressValidator = new EmailAddressValidator();
    }

    @AfterEach
    void tearDown() {
        emailAddressValidator = null;
    }

    @Nested
    class isValidEmailTester {

        @Test
        void should_ReturnFalse_When_NoEmail() { assertFalse(emailAddressValidator.isValidEmailAddress(null)); }

        @ParameterizedTest
        @ValueSource(strings = { "", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "johnsmithgmail.com", "@!#@mail.com"})
        void should_ReturnFalse_When_InvalidEmailFormat(String email) {
            assertFalse(emailAddressValidator.isValidEmailAddress(email));
        }

        @ParameterizedTest
        @ValueSource(strings = { "john.smith@sgmail.com", "raposoalexander@gmail.com" })
        void should_ReturnTrue_When_ValidEmailFormat(String email) {
            assertTrue(emailAddressValidator.isValidEmailAddress(email));
        }
    }
}
