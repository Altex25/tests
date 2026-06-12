package com.example;

import java.util.ArrayList;
import java.util.List;

public class Frame {
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
        return false;
    }

    private boolean canRoll() {
        return false;
    }

    private int remainingPins() {
        return 0;
    }

    private boolean isStrike(int rollIndex) {
        return false;
    }

    private boolean hasStrike() {
        return false;
    }

    private boolean hasSpare() {
        return false;
    }

    public int getScore() {
        return 0;
    }

    public boolean isLastFrame() {
        return false;
    }

    public List<Roll> getRolls() {
        return rolls;
    }
}
