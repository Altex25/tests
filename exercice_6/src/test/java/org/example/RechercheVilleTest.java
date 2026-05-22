package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RechercheVilleTest {

    private RechercheVille rechercheVille;

    @BeforeEach
    void setUp() {
        rechercheVille = new RechercheVille();
    }

    @Nested
    @DisplayName("Étape 1 – Moins de 2 caractères")
    class Etape1MoinsDe2Caracteres {

        @Test
        @DisplayName("Chaîne vide → NotFoundException")
        void shouldThrowNotFoundExceptionWhenSearchTextIsEmpty() {
            assertThrows(NotFoundException.class,
                    () -> rechercheVille.rechercher(""));
        }

        @Test
        @DisplayName("Un seul caractère → NotFoundException")
        void shouldThrowNotFoundExceptionWhenSearchTextHasOneCharacter() {
            assertThrows(NotFoundException.class,
                    () -> rechercheVille.rechercher("P"));
        }

        @Test
        @DisplayName("null → NotFoundException")
        void shouldThrowNotFoundExceptionWhenSearchTextIsNull() {
            assertThrows(NotFoundException.class,
                    () -> rechercheVille.rechercher(null));
        }

        @ParameterizedTest(name = "\"{0}\" → NotFoundException")
        @ValueSource(strings = {"", "A", "z", "1"})
        @DisplayName("Tout texte de moins de 2 caractères → NotFoundException")
        void shouldThrowNotFoundExceptionForAnyTextShorterThan2Chars(String mot) {
            assertThrows(NotFoundException.class,
                    () -> rechercheVille.rechercher(mot));
        }
    }

    @Nested
    @DisplayName("Étape 2 – Recherche par préfixe")
    class Etape2Prefixe {

        @Test
        @DisplayName("\"Va\" → Valence et Vancouver")
        void shouldReturnValenceAndVancouverWhenSearchTextIsVa() {
            List<String> result = rechercheVille.rechercher("Va");
            assertAll(
                    () -> assertTrue(result.contains("Valence")),
                    () -> assertTrue(result.contains("Vancouver"))
            );
        }

        @Test
        @DisplayName("\"Pa\" → Paris")
        void shouldReturnParisWhenSearchTextIsPa() {
            assertTrue(rechercheVille.rechercher("Pa").contains("Paris"));
        }

        @Test
        @DisplayName("\"Zz\" → liste vide (aucune correspondance)")
        void shouldReturnEmptyListWhenNoCityMatchesPrefix() {
            assertTrue(rechercheVille.rechercher("Zz").isEmpty());
        }

        @Test
        @DisplayName("\"Va\" → Paris et Rome exclus du résultat")
        void shouldNotReturnCitiesThatDoNotMatchPrefix() {
            List<String> result = rechercheVille.rechercher("Va");
            assertAll(
                    () -> assertFalse(result.contains("Paris")),
                    () -> assertFalse(result.contains("Rome"))
            );
        }
    }

    @Nested
    @DisplayName("Étape 3 – Insensibilité à la casse")
    class Etape3Casse {

        @Test
        @DisplayName("\"pa\" (minuscules) → Paris")
        void shouldReturnParisWhenSearchTextIsLowercase() {
            assertTrue(rechercheVille.rechercher("pa").contains("Paris"));
        }

        @Test
        @DisplayName("\"PA\" (majuscules) → Paris")
        void shouldReturnParisWhenSearchTextIsUppercase() {
            assertTrue(rechercheVille.rechercher("PA").contains("Paris"));
        }

        @Test
        @DisplayName("\"va\" (minuscules) → Valence et Vancouver")
        void shouldReturnValenceAndVancouverWhenSearchTextIsLowercase() {
            List<String> result = rechercheVille.rechercher("va");
            assertAll(
                    () -> assertTrue(result.contains("Valence")),
                    () -> assertTrue(result.contains("Vancouver"))
            );
        }

        @Test
        @DisplayName("\"rO\" (casse mixte) → Rome")
        void shouldReturnRomeWhenSearchTextIsMixedCase() {
            assertTrue(rechercheVille.rechercher("rO").contains("Rome"));
        }
    }

    @Nested
    @DisplayName("Étape 4 – Recherche partielle")
    class Etape4Partielle {

        @Test
        @DisplayName("\"ape\" contenu dans Budapest → Budapest retourné")
        void shouldReturnBudapestWhenSearchTextIsApe() {
            assertTrue(rechercheVille.rechercher("ape").contains("Budapest"));
        }

        @Test
        @DisplayName("\"erd\" contenu dans Amsterdam → Amsterdam retourné")
        void shouldReturnAmsterdamWhenSearchTextIsErd() {
            assertTrue(rechercheVille.rechercher("erd").contains("Amsterdam"));
        }

        @Test
        @DisplayName("\"an\" contenu dans Vancouver, Amsterkdam et Bangko → les 3 retournés")
        void shouldReturnMultipleCitiesWhenSearchTextIsSharedInfix() {
            List<String> result = rechercheVille.rechercher("an");
            assertAll(
                    () -> assertTrue(result.contains("Vancouver")),
                    () -> assertTrue(result.contains("Bangkok"))
            );
        }

        @Test
        @DisplayName("\"APE\" (majuscules) contenu dans Budapest → Budapest retourné")
        void shouldBeCaseInsensitiveForPartialSearch() {
            assertTrue(rechercheVille.rechercher("APE").contains("Budapest"));
        }
    }

    @Nested
    @DisplayName("Étape 5 – Astérisque renvoie toutes les villes")
    class Etape5Asterisque {

        @Test
        @DisplayName("\"*\" → les 16 villes sont retournées")
        void shouldReturn16CitiesWhenSearchTextIsAsterisk() {
            assertEquals(16, rechercheVille.rechercher("*").size());
        }

        @Test
        @DisplayName("\"*\" → toutes les villes connues sont présentes")
        void shouldContainEveryKnownCityWhenSearchTextIsAsterisk() {
            List<String> result = rechercheVille.rechercher("*");
            assertAll(
                    () -> assertTrue(result.contains("Paris")),
                    () -> assertTrue(result.contains("Budapest")),
                    () -> assertTrue(result.contains("Skopje")),
                    () -> assertTrue(result.contains("Rotterdam")),
                    () -> assertTrue(result.contains("Valence")),
                    () -> assertTrue(result.contains("Vancouver")),
                    () -> assertTrue(result.contains("Amsterdam")),
                    () -> assertTrue(result.contains("Vienne")),
                    () -> assertTrue(result.contains("Sydney")),
                    () -> assertTrue(result.contains("New York")),
                    () -> assertTrue(result.contains("Londres")),
                    () -> assertTrue(result.contains("Bangkok")),
                    () -> assertTrue(result.contains("Hong Kong")),
                    () -> assertTrue(result.contains("Dubaï")),
                    () -> assertTrue(result.contains("Rome")),
                    () -> assertTrue(result.contains("Istanbul"))
            );
        }

        @Test
        @DisplayName("\"*\" → aucune exception levée (1 seul caractère autorisé)")
        void shouldNotThrowExceptionWhenSearchTextIsAsterisk() {
            assertDoesNotThrow(() -> rechercheVille.rechercher("*"));
        }
    }
}