package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class EventType {
    LocalDateTime timeOfEvent;
    String eventType;
    String roomNumber;

    public EventType(LocalDateTime timeOfEvent, String eventType, String roomNumber) {
        this.timeOfEvent = timeOfEvent;
        this.eventType = eventType;
        this.roomNumber = roomNumber;
    }
}
