package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
public class DoorOpen extends EventType {
    public DoorOpen(LocalDateTime timeOfEvent, String eventType, String roomNumber) {
        super(timeOfEvent, eventType, roomNumber);
    }
}
