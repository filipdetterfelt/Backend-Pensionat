package com.example.backendpensionat.Models.RoomEventHappenings;


import java.time.LocalDateTime;
public class CleaningEnded extends eventType{
    public CleaningEnded(LocalDateTime timeOfEvent, String eventType) {
        super(timeOfEvent, eventType);
    }
}
