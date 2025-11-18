package com.comp2042;

public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;
    private EventSource source;

    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventSource getEventSource() {
        return eventSource;
    }

    public EventSource getSource() {
        return source;
    }

}
