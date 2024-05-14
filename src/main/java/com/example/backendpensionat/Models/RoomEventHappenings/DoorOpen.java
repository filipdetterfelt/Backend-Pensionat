package com.example.backendpensionat.Models.RoomEventHappenings;

import java.time.LocalDateTime;

public class DoorOpen extends EventType {
    public DoorOpen(LocalDateTime timeOfEvent, String eventType) {
        super(timeOfEvent, eventType);
    }
}
