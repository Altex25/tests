package com.example;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private static final int MAX_PINS = 10;

    private int score;
    private final boolean lastFrame;
    private final IGenerateur generateur;
    private final List<Roll> rolls;

    public Frame(IGenerateur generateur, boolean lastFrame) {
        this.lastFrame = lastFrame;
        this.generateur = generateur;
        this.rolls = new ArrayList<>();
    }

    public boolean makeRoll() {
        if (!canRoll()) {
            return false;
        }

        int pins = generateur.randomPin(remainingPins());
        rolls.add(new Roll(pins));
        score += pins;
        return true;
    }

    private boolean canRoll() {
        int rollCount = rolls.size();

        if (lastFrame) {
            if (rollCount >= 3) {
                return false;
            }
            if (rollCount == 2) {
                return isStrike(0) || isSpare();
            }
            return true;
        }

        if (rollCount >= 2) {
            return false;
        }
        if (rollCount == 1) {
            return !isStrike(0);
        }
        return true;
    }

    private int remainingPins() {
        int rollCount = rolls.size();
        if (rollCount == 0) {
            return MAX_PINS;
        }

        if (!lastFrame) {
            return MAX_PINS - rolls.get(0).getPins();
        }

        if (rollCount == 1) {
            return isStrike(0) ? MAX_PINS : MAX_PINS - rolls.get(0).getPins();
        }

        if (isStrike(0)) {
            return isStrike(1) ? MAX_PINS : MAX_PINS - rolls.get(1).getPins();
        }
        return MAX_PINS;
    }

    private boolean isStrike(int rollIndex) {
        return rolls.get(rollIndex).getPins() == MAX_PINS;
    }

    private boolean isSpare() {
        return rolls.get(0).getPins() + rolls.get(1).getPins() == MAX_PINS;
    }

    public int getScore() {
        return score;
    }
}
