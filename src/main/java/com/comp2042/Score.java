package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty line = new SimpleIntegerProperty(0);

    public IntegerProperty scoreProperty() {
        return score;
    }

    public IntegerProperty lineProperty() {
        return line;
    }

    // increase score
    public void add(int value) {
        score.set(score.get() + value);
    }

    // increase line count
    public void addLine(int value) {
        line.set(line.get() + value);
    }

    // reset both score and line to 0
    public void reset() {
        score.set(0);
        line.set(0);
    }
}
