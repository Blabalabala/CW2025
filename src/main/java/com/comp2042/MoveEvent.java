package com.comp2042;

/**
 * Represents a movement event for a Tetris brick.
 * <p>
 * Each event contains the type of movement (left, right, down, rotate, hard drop)
 * and the source of the event (user input or automatic thread).
 * </p>
 * <p>
 * This class is immutable except for the {@code source} field which can be set separately if needed.
 * </p>
 */
public final class MoveEvent {

    private final EventType eventType;
    private final EventSource eventSource;
    private EventSource source;

    /**
     * Constructs a new MoveEvent with the specified type and source.
     *
     * @param eventType the type of movement (e.g., LEFT, RIGHT, DOWN, ROTATE, HARD_DROP)
     * @param eventSource the origin of the event (e.g., USER, THREAD)
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Returns the type of movement associated with this event.
     *
     * @return the EventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the origin of the event.
     *
     * @return the EventSource
     */
    public EventSource getEventSource() {
        return eventSource;
    }

    /**
     * Returns the optional secondary source of the event.
     *
     * @return the secondary EventSource, or null if not set
     */
    public EventSource getSource() {
        return source;
    }
}
