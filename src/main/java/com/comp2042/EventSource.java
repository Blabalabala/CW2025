package com.comp2042;

/**
 * Enum representing the source of a game event.
 * <p>
 * USER: event triggered by the player (keyboard input).
 * THREAD: event triggered by the game engine or timer.
 */
public enum EventSource {
    USER,
    THREAD
}
