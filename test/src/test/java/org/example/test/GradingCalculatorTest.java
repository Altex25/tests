package org.example.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradingCalculatorTest {

    @Test
    void assertGetGradeScore95Presence90GetA() {
        GradingCalculator calculator = new GradingCalculator(95, 90);

        int grade = calculator.getGrade();

        assertEquals('A', grade);
    }

    @Test
    void assertGetGradeScore85Presence90GetB() {
        GradingCalculator calculator = new GradingCalculator(85, 90);

        int grade = calculator.getGrade();

        assertEquals('B', grade);
    }

    @Test
    void assertGetGradeScore65Presence90GetC() {
        GradingCalculator calculator = new GradingCalculator(65, 90);

        int grade = calculator.getGrade();

        assertEquals('C', grade);
    }

    @Test
    void assertGetGradeScore95Presence65GetB() {
        GradingCalculator calculator = new GradingCalculator(95, 65);

        int grade = calculator.getGrade();

        assertEquals('B', grade);
    }

    @Test
    void assertGetGradeScore95Presence55GetF() {
        GradingCalculator calculator = new GradingCalculator(95, 55);

        int grade = calculator.getGrade();

        assertEquals('F', grade);
    }

    @Test
    void assertGetGradeScore65Presence55GetF() {
        GradingCalculator calculator = new GradingCalculator(65, 55);

        int grade = calculator.getGrade();

        assertEquals('F', grade);
    }

    @Test
    void assertGetGradeScore50Presence90GetF() {
        GradingCalculator calculator = new GradingCalculator(50, 90);

        int grade = calculator.getGrade();

        assertEquals('F', grade);
    }
}
