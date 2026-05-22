package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FibTest {

    @Nested
    @DisplayName("Tests with range equal 1")
    class FibTestWithRange1 {
        private final Fib fib = new Fib(1);

        @Test
        @DisplayName("Assert results is not empty")
        void assertGetFibSeriesWithRange1IsNotEmpty() {
            List<Integer> results = fib.getFibSeries();
            assertFalse(results.isEmpty());
        }

        @Test
        @DisplayName("Assert only value in list is 0")
        void assertGetFibSeriesWithRange1Is0() {
            int expected = 0;
            List<Integer> results = fib.getFibSeries();

            assertEquals(expected, results.getFirst());
        }
    }

    @Nested
    @DisplayName("Tests with range equal 6")
    class FibTestWithRange6 {
        private final Fib fib = new Fib(6);

        @Test
        @DisplayName("Assert results contains 3")
        void assertGetFibSeriesWithRange6Contains3() {
            List<Integer> results = fib.getFibSeries();
            assertTrue(results.contains(3));
        }

        @Test
        @DisplayName("Assert results contains 6 elements")
        void assertGetFibSeriesWithRange6Contains6Elements() {
            List<Integer> results = fib.getFibSeries();
            assertEquals(6, results.size());
        }

        @Test
        @DisplayName("Assert results dis not contains 4")
        void assertGetFibSeriesWithRange6DoNotContain4() {
            List<Integer> results = fib.getFibSeries();
            assertFalse(results.contains(4));
        }

        @Test
        @DisplayName("Assert results contains {0, 1, 1, 2, 3, 5}")
        void assertGetFibSeriesWithRange6Is011235() {
            List<Integer> results = fib.getFibSeries();

            assertAll("Fib values",
                    () -> assertEquals(0, results.getFirst()),
                    () -> assertEquals(1, results.get(1)),
                    () -> assertEquals(1, results.get(2)),
                    () -> assertEquals(2, results.get(3)),
                    () -> assertEquals(3, results.get(4)),
                    () -> assertEquals(5, results.get(5))
            );
        }

        @Test
        @DisplayName("Assert results is sorted asc")
        void assertGetFibSeriesWithRange6IsSortedAsc() {
            List<Integer> results = fib.getFibSeries();

            for (int i = 0; i < results.size() - 1; i++) {
                assertTrue(results.get(i) <= results.get(i + 1));
            }
        }
    }
}
