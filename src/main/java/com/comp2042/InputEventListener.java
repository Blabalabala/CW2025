package com.comp2042;

/**
 * Interface for handling input events in the Tetris game.
 * Implementations of this interface respond to user or thread actions
 * like moving or rotating bricks, performing hard drops, and creating new games.
 */
public interface InputEventListener {

    /**
     * Handles the event when a brick moves downward.
     *
     * @param event the move event (user or automatic thread)
     * @return DownData containing updated board state, ghost piece info, and lock status
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles the event when a brick moves left.
     *
     * @param event the move event
     * @return the updated ViewData of the brick
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles the event when a brick moves right.
     *
     * @param event the move event
     * @return the updated ViewData of the brick
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles the event when a brick rotates.
     *
     * @param event the move event
     * @return the updated ViewData of the brick
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Handles the event when a brick is dropped instantly to the bottom (hard drop).
     *
     * @param event the move event
     * @return DownData containing updated board state and lock information
     */
    DownData onHardDropEvent(MoveEvent event);

    /**
     * Creates a new game, resetting the board and score.
     */
    void createNewGame();

    /**
     * Checks whether a brick can move down to a new Y position without colliding
     * with existing blocks or going out of bounds.
     *
     * @param brick the current brick's ViewData
     * @param newY the proposed Y position
     * @return true if the brick can safely move down; false otherwise
     */
    boolean canMoveDown(ViewData brick, int newY);
}
