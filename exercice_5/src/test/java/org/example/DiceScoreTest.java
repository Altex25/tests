package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiceScoreTest {
    @Mock
    private Ide ideMock;
    private DiceScore diceScore;

    @BeforeEach
    void setUp() {
        diceScore = new DiceScore(ideMock);
    }

    @Test
    void shouldReturn30WhenBothDiceAre6() {
        when(ideMock.getRoll()).thenReturn(6, 6);
        assertEquals(30, diceScore.getScore());
    }

    @Test
    void shouldReturnValueTimes2Plus10WhenBothDiceAreEqualAndNot6() {
        when(ideMock.getRoll()).thenReturn(3, 3);
        assertEquals(16, diceScore.getScore());
    }

    @Test
    void shouldReturnSecondDiceWhenFirstIsLower() {
        when(ideMock.getRoll()).thenReturn(2, 5);
        assertEquals(5, diceScore.getScore());
    }

    @Test
    void shouldReturnFirstDiceWhenSecondIsLower() {
        when(ideMock.getRoll()).thenReturn(5, 2);
        assertEquals(5, diceScore.getScore());
    }
}
