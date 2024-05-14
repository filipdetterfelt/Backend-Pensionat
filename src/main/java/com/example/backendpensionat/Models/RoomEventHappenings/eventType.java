package com.example.backendpensionat.Models.RoomEventHappenings;

import java.time.LocalDateTime;

public abstract class eventType {
    LocalDateTime timeOfEvent;
    String eventType;

    public eventType(LocalDateTime timeOfEvent, String eventType) {
        this.timeOfEvent = timeOfEvent;
        this.eventType = eventType;
    }

    public LocalDateTime getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(LocalDateTime timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
