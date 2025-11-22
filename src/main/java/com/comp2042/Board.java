package com.comp2042;

/**
 * Represents the game board for the brick-dropping game.
 * This interface defines all actions related to brick movement,
 * rotation, board updates, and score handling.
 */
public interface Board {

    /**
     * Moves the current brick one row down.
     *
     * @return true if the move was successful, false if a collision occurs
     */
    boolean moveBrickDown();

    /**
     * Moves the current brick one column to the left.
     *
     * @return true if the move was successful, false if a collision occurs
     */
    boolean moveBrickLeft();

    /**
     * Moves the current brick one column to the right.
     *
     * @return true if the move was successful, false if a collision occurs
     */
    boolean moveBrickRight();

    /**
     * Rotates the current brick to the next rotation state.
     *
     * @return true if the rotation is valid, false if it causes collision
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new falling brick and places it at the spawn position.
     *
     * @return true if the brick collides immediately, false otherwise
     */
    boolean createNewBrick();

    /**
     * Returns the internal board grid containing all placed bricks.
     *
     * @return a 2D integer matrix representing the board
     */
    int[][] getBoardMatrix();

    /**
     * Retrieves all data needed to render the game, including the current shape and position.
     *
     * @return a ViewData object containing shape and location information
     */
    ViewData getViewData();

    /**
     * Merges the current falling brick into the board's background matrix.
     */
    void mergeBrickToBackground();

    /**
     * Clears completed rows and updates the score accordingly.
     *
     * @return a ClearRow object containing information about cleared rows
     */
    ClearRow clearRows();

    /**
     * Returns the current score handler.
     *
     * @return the score object associated with this game
     */
    Score getScore();

    /**
     * Resets the board and starts a new game.
     */
    void newGame();

    /**
     * Returns information about the next shape to be spawned.
     *
     * @return the next shape information, or null if no next brick exists
     */
    NextShapeInfo getNextShape();

    /**
     * Holds the current brick and swaps it with the held brick (if any).
     * Can only be called once per piece until a new piece is spawned.
     *
     * @return HoldShapeInfo containing the held brick's shape and color, or null if hold was already used for this piece
     */
    HoldShapeInfo holdBrick();

    /**
     * Returns information about the currently held brick.
     *
     * @return HoldShapeInfo of the held brick, or null if no brick is held
     */
    HoldShapeInfo getHeldShape();
}
