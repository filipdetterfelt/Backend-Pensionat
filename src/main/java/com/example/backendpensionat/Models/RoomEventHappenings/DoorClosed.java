package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
public class DoorClosed extends EventType {
    public DoorClosed(LocalDateTime timeOfEvent, String eventType, String roomNumber, String cleaningByUser) {
        super(timeOfEvent, eventType, roomNumber, cleaningByUser);
    }
}
