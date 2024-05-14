package com.example.backendpensionat.Models.RoomEventHappenings;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
public class CleaningEnded extends EventType {

    public CleaningEnded(LocalDateTime timeOfEvent, String eventType, String roomNumber, String cleaningByUser) {
        super(timeOfEvent, eventType, roomNumber, cleaningByUser);

    }
}
