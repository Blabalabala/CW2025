package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents the player's score and the number of lines cleared during the game.
 * Provides methods to update and reset these values.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty line = new SimpleIntegerProperty(0);

    /**
     * Returns the score property used for binding in the UI.
     * @return the score property
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Returns the line count property used for binding in the UI.
     * @return the line count property
     */
    public IntegerProperty lineProperty() {
        return line;
    }

    /**
     * Increases the current score by the specified value.
     * @param value the amount to add to the score
     */
    public void add(int value) {
        score.set(score.get() + value);
    }

    /**
     * Increases the number of cleared lines by the specified value.
     * @param value the number of lines to add
     */
    public void addLine(int value) {
        line.set(line.get() + value);
    }

    /**
     * Resets both score and cleared line count to zero.
     */
    public void reset() {
        score.set(0);
        line.set(0);
    }
}
