package com.comp2042;

/**
 * Enum representing different types of game events in Tetris.
 * <p>
 * DOWN: move the current brick down by one row.
 * LEFT: move the current brick to the left.
 * RIGHT: move the current brick to the right.
 * ROTATE: rotate the current brick.
 * HARD_DROP: drop the current brick instantly to the bottom.
 */
public enum EventType {
    DOWN,
    LEFT,
    RIGHT,
    ROTATE,
    HARD_DROP
}
