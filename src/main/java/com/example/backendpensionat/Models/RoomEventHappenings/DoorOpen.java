package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class DoorOpen extends eventType{
    public DoorOpen(LocalDateTime timeOfEvent, String eventType) {
        super(timeOfEvent, eventType);
    }
}
