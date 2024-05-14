package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class CleaningStarted extends EventType {
    private String cleaningByUser;

    public CleaningStarted(LocalDateTime timeOfEvent, String eventType, String roomNumber, String cleaningByUser) {
        super(timeOfEvent, eventType, roomNumber);
        this.cleaningByUser = cleaningByUser;
    }
}
