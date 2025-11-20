package com.comp2042;

/**
 * Holds the data necessary to render the current Tetris piece and the next piece in the UI.
 * <p>
 * Provides immutable access to the brick's shape matrix, its current position,
 * and the next brick's shape matrix.
 * </p>
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;

    /**
     * Constructs a new ViewData object with the specified brick data, position, and next brick.
     *
     * @param brickData     the 2D array representing the current brick's shape
     * @param xPosition     the x-coordinate of the current brick on the board
     * @param yPosition     the y-coordinate of the current brick on the board
     * @param nextBrickData the 2D array representing the next brick's shape
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    /**
     * Returns a deep copy of the current brick's shape matrix.
     *
     * @return a 2D int array representing the current brick's shape
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    /**
     * Returns the x-coordinate of the current brick on the board.
     *
     * @return the x position
     */
    public int getxPosition() {
        return xPosition;
    }

    /**
     * Returns the y-coordinate of the current brick on the board.
     *
     * @return the y position
     */
    public int getyPosition() {
        return yPosition;
    }

    /**
     * Returns a deep copy of the next brick's shape matrix for preview purposes.
     *
     * @return a 2D int array representing the next brick's shape
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
