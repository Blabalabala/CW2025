package com.comp2042;

/**
 * Represents the result of clearing completed rows from the game board.
 * <p>
 * This class stores how many rows were removed, the updated matrix after
 * removal, and any score bonus associated with the row clear.
 * </p>
 */
public final class ClearRow {

    /** Number of rows that were removed. */
    private final int linesRemoved;

    /** The updated board matrix after removing the rows. */
    private final int[][] newMatrix;

    /** Additional score bonus awarded for this clear. */
    private final int scoreBonus;

    /**
     * Creates a new {@code ClearRow} object containing the results of a row clear.
     *
     * @param linesRemoved the number of completed rows removed from the board
     * @param newMatrix the updated board matrix after row removal
     * @param scoreBonus bonus score awarded for clearing rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of rows removed.
     *
     * @return number of cleared rows
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the updated board matrix after row removal.
     * <p>
     * A defensive copy is returned to prevent external modification.
     * </p>
     *
     * @return a copy of the new board matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus awarded for clearing the rows.
     *
     * @return score bonus value
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
