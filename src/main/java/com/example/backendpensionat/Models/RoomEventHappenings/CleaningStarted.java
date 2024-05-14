package com.example.backendpensionat.Models.RoomEventHappenings;

import java.time.LocalDateTime;

public class CleaningStarted extends EventType {

    public CleaningStarted(LocalDateTime timeOfEvent, String eventType, String roomNumber) {
        super(timeOfEvent, eventType, roomNumber);
    }
}
