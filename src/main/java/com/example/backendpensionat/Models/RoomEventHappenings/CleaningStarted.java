package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class CleaningStarted extends eventType{

    public CleaningStarted(LocalDateTime timeOfEvent, String eventType) {
        super(timeOfEvent, eventType);
    }
}
