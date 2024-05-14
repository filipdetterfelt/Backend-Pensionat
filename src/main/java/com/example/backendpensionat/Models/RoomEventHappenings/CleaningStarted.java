package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.AllArgsConstructor;


import java.time.LocalDateTime;
@AllArgsConstructor
public class CleaningStarted extends EventType {

    public CleaningStarted(LocalDateTime timeOfEvent, String eventType, String roomNumber, String cleaningByUser) {
        super(timeOfEvent, eventType, roomNumber, cleaningByUser);
    }
}
