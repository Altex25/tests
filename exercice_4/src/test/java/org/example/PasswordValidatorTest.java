package org.example;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Nested
    @DisplayName("Tests classiques")
    class ClassicTests {

        @Test
        @DisplayName("Password1! → valide")
        void assertPassword1ExclamationIsValid() {
            assertTrue(validator.isValid("Password1!"));
        }

        @Test
        @DisplayName("Admin2024@ → valide")
        void assertAdmin2024AtIsValid() {
            assertTrue(validator.isValid("Admin2024@"));
        }

        @Test
        @DisplayName("short1! (7 chars) → invalide")
        void assertTooShortPasswordIsInvalid() {
            assertFalse(validator.isValid("short1!"));
        }

        @Test
        @DisplayName("PASSWORD1! (pas de minuscule) → invalide")
        void assertNoLowercaseInPasswordIsInvalid() {
            assertFalse(validator.isValid("PASSWORD1!"));
        }

        @Test
        @DisplayName("password1! (pas de majuscule) → invalide")
        void assertNoUppercaseInPasswordIsInvalid() {
            assertFalse(validator.isValid("password1!"));
        }

        @Test
        @DisplayName("Password! (pas de chiffre) → invalide")
        void assertNoDigitIsInvalid() {
            assertFalse(validator.isValid("Password!"));
        }

        @Test
        @DisplayName("Password1 (pas de caractère spécial) → invalide")
        void assertNoSpecialCharIsInvalid() {
            assertFalse(validator.isValid("Password1"));
        }

        @Test
        @DisplayName("null → invalide")
        void assertNullIsInvalid() {
            assertFalse(validator.isValid(null));
        }
    }

    @Nested
    @DisplayName("Tests paramétrés @CsvSource")
    class CsvSourceTests {

        @ParameterizedTest(name = "[{index}] ''{0}'' → valide={1}")
        @CsvSource({
                "Password1!,  true",
                "Admin2024@,  true",
                "short1!,     false",
                "PASSWORD1!,  false",
                "password1!,  false",
                "Password!,   false",
                "Password1,   false"
        })
        @DisplayName("Table de vérité isValid()")
        void assertIsValidTable(String password, boolean expected) {
            assertEquals(expected, validator.isValid(password));
        }

        @ParameterizedTest(name = "[{index}] ''{0}'' → message=''{1}''")
        @CsvSource({
                "short1!,    Password must contain at least 8 characters",
                "PASSWORD1!, Password must contain at least one lowercase letter",
                "password1!, Password must contain at least one uppercase letter",
                "Password!,  Password must contain at least one digit",
                "Password1,  Password must contain at least one special character",
                "Password1!, Password is valid"
        })
        @DisplayName("Messages attendus par règle")
        void assertErrorMessageTable(String password, String expectedMessage) {
            assertEquals(expectedMessage, validator.getErrorMessage(password));
        }
    }

    @Nested
    @DisplayName("Tests with @ValueSource")
    class ValueSourceTests {

        @ParameterizedTest(name = "[{index}] ''{0}'' doit être valide")
        @ValueSource(strings = {
                "Password1!",
                "Admin2024@",
                "MyP@ss99",
                "Hello#42world",
                "UPPER1lower$",
                "Str0ng%Pass"
        })
        @DisplayName("Mots de passe valides variés")
        void assertValidPasswords(String password) {
            assertTrue(validator.isValid(password),
                    "Expected valid but got: " + validator.getErrorMessage(password));
        }

        @ParameterizedTest(name = "[{index}] caractère spécial ''{0}'' doit être accepté")
        @ValueSource(strings = {
                "Secure01!",
                "Secure01@",
                "Secure01#",
                "Secure01$",
                "Secure01%"
        })
        @DisplayName("Chaque caractère spécial autorisé est accepté")
        void assertEachSpecialCharAccepted(String password) {
            assertTrue(validator.isValid(password));
        }
    }

    @Nested
    @DisplayName("Test with @MethodSource")
    class MethodSourceTests {

        static Stream<Arguments> providePasswordsAndMessages() {
            return Stream.of(
                    Arguments.of(null,          PasswordValidator.MSG_NULL),
                    Arguments.of("Ab1@",        PasswordValidator.MSG_LENGTH),
                    Arguments.of("ABCDEF1@",    PasswordValidator.MSG_LOWER),
                    Arguments.of("abcdef1@",    PasswordValidator.MSG_UPPER),
                    Arguments.of("Abcdefgh@",   PasswordValidator.MSG_DIGIT),
                    Arguments.of("Abcdefg1",    PasswordValidator.MSG_SPECIAL),
                    Arguments.of("Password1!",  PasswordValidator.MSG_VALID)
            );
        }

        @ParameterizedTest(name = "[{index}] ''{0}'' → ''{1}''")
        @MethodSource("providePasswordsAndMessages")
        @DisplayName("Vérification exhaustive des messages via @MethodSource")
        void assertMultipleCorrectMessages(String password, String expectedMessage) {
            assertEquals(expectedMessage, validator.getErrorMessage(password));
        }

        static Stream<String> provideInvalidPasswords() {
            return Stream.of(
                    "short",
                    "alllowercase1!",
                    "ALLUPPERCASE1!",
                    "NoDigitHere!",
                    "NoSpecial1234"
            );
        }

        @ParameterizedTest(name = "[{index}] ''{0}'' doit être invalide")
        @MethodSource("provideInvalidPasswords")
        @DisplayName("Mots de passe invalides via @MethodSource")
        void assertMultipleInvalidPasswords(String password) {
            assertFalse(validator.isValid(password));
        }
    }

    @Nested
    @DisplayName("Bonus")
    class NullAndEmptySourceTests {

        @ParameterizedTest(name = "[{index}] entrée nulle ou vide → invalide")
        @NullAndEmptySource
        @DisplayName("null et chaîne vide sont toujours invalides")
        void assertNullOrEmptyIsInvalid(String password) {
            assertFalse(validator.isValid(password));
        }

        @ParameterizedTest(name = "[{index}] entrée nulle ou vide → message non vide")
        @NullAndEmptySource
        @DisplayName("null et chaîne vide retournent un message d'erreur non vide")
        void assertNullOrEmptyHasErrorMessage(String password) {
            String msg = validator.getErrorMessage(password);
            assertNotNull(msg);
            assertNotEquals(PasswordValidator.MSG_VALID, msg);
        }
    }
}