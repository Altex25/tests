package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FrameTest {

    private IGenerateur generateur;

    @BeforeEach
    void setUp() {
        generateur = mock(IGenerateur.class);
    }

    @Test
    void shouldIncreaseScoreWhenFirstRollIsMadeInStandardFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(4);
        Frame frame = new Frame(generateur, false);

        boolean rolled = frame.makeRoll();

        assertTrue(rolled);
        assertEquals(4, frame.getScore());
    }

    @Test
    void shouldIncreaseScoreWhenSecondRollIsMadeInStandardFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 3);
        Frame frame = new Frame(generateur, false);

        frame.makeRoll();
        boolean secondRoll = frame.makeRoll();

        assertTrue(secondRoll);
        assertEquals(7, frame.getScore());
    }

    @Test
    void shouldRejectSecondRollWhenStandardFrameStartsWithStrike() {
        when(generateur.randomPin(anyInt())).thenReturn(10);
        Frame frame = new Frame(generateur, false);

        frame.makeRoll();
        boolean secondRoll = frame.makeRoll();

        assertFalse(secondRoll);
    }

    @Test
    void shouldRejectThirdRollWhenStandardFrameAlreadyHasTwoRolls() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 3);
        Frame frame = new Frame(generateur, false);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertFalse(thirdRoll);
    }

    @Test
    void shouldIncreaseScoreWhenSecondRollIsMadeAfterStrikeInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        boolean secondRoll = frame.makeRoll();

        assertTrue(secondRoll);
        assertEquals(15, frame.getScore());
    }

    @Test
    void shouldAcceptThirdRollWhenLastFrameStartsWithStrike() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5, 3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertTrue(thirdRoll);
    }

    @Test
    void shouldAcceptSecondRollWhenLastFrameStartsWithStrike() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        boolean secondRoll = frame.makeRoll();

        assertTrue(secondRoll);
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterStrikeInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5, 3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertTrue(thirdRoll);
        assertEquals(18, frame.getScore());
    }

    @Test
    void shouldAcceptThirdRollWhenLastFrameStartsWithSpare() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 6, 5);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertTrue(thirdRoll);
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterSpareInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 6, 5);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertTrue(thirdRoll);
        assertEquals(15, frame.getScore());
    }

    @Test
    void shouldRejectThirdRollWhenLastFrameHasNoStrikeOrSpare() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertFalse(thirdRoll);
    }

    @Test
    void shouldRejectFourthRollInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5, 3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();
        boolean fourthRoll = frame.makeRoll();

        assertFalse(fourthRoll);
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterDoubleStrikeInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 10, 3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean thirdRoll = frame.makeRoll();

        assertTrue(thirdRoll);
        assertEquals(23, frame.getScore());
    }
}
