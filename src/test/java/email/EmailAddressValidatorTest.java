package email;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
        void should_ReturnFalse_WhenNoEmail() { assertFalse(emailAddressValidator.isValidEmailAddress(null)); }

        @ParameterizedTest
        @ValueSource(strings = { "", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "johnsmithgmail.com", "@!#@mail.com"})
        void should_ReturnFalse_WhenInvalidEmailFormat(String email) {
            assertFalse(emailAddressValidator.isValidEmailAddress(email));
        }

        @ParameterizedTest
        @ValueSource(strings = { "john.smith@sgmail.com", "raposoalexander@gmail.com" })
        void should_ReturnTrue_WhenValidEmailFormat(String email) {
            assertTrue(emailAddressValidator.isValidEmailAddress(email));
        }
    }
}
