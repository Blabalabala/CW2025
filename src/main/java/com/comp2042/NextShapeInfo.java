package com.comp2042;

/**
 * Represents the next Tetris piece that will appear in the game.
 * <p>
 * Stores the shape of the piece as a 2D matrix and its associated position index.
 * Provides a deep copy of the shape to prevent external modification.
 * </p>
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructs a new NextShapeInfo object with the specified shape and position.
     *
     * @param shape    a 2D array representing the piece's shape
     * @param position an integer representing the position/index of the piece
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }

    /**
     * Returns a deep copy of the Tetris piece shape.
     *
     * @return a 2D int array representing the shape
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Returns the position/index of the Tetris piece.
     *
     * @return the position as an integer
     */
    public int getPosition() {
        return position;
    }
}
