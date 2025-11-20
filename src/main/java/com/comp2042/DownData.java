package com.comp2042;

/**
 * Encapsulates the result of a brick moving down in the game.
 * Contains information about cleared rows, the current view data,
 * and whether the brick has been locked in place.
 */
public final class DownData {

    private final ClearRow clearRow;
    private final ViewData viewData;
    private final boolean locked;

    /**
     * Constructs a DownData object with the specified details.
     *
     * @param clearRow information about rows cleared after the move
     * @param viewData the current state of the falling brick
     * @param locked whether the brick is locked in its current position
     */
    public DownData(ClearRow clearRow, ViewData viewData, boolean locked) {
        this.clearRow = clearRow;
        this.viewData = viewData;
        this.locked = locked;
    }

    /**
     * Returns the cleared row information.
     *
     * @return the ClearRow object
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Returns the current view data of the brick.
     *
     * @return the ViewData object
     */
    public ViewData getViewData() {
        return viewData;
    }

    /**
     * Indicates whether the brick has been locked in place.
     *
     * @return true if the brick is locked; false otherwise
     */
    public boolean isLocked() {
        return locked;
    }
}
