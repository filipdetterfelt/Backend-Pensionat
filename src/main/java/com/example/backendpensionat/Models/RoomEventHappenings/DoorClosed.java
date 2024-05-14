package com.example.backendpensionat.Models.RoomEventHappenings;

import java.time.LocalDateTime;

public class DoorClosed extends EventType {
    public DoorClosed(LocalDateTime timeOfEvent, String eventType) {
        super(timeOfEvent, eventType);
    }
}
