package com.example.backendpensionat.Models;

import com.example.backendpensionat.Models.RoomEventHappenings.CleaningEnded;
import com.example.backendpensionat.Models.RoomEventHappenings.CleaningStarted;
import com.example.backendpensionat.Models.RoomEventHappenings.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomEvents {

    @Id
    @GeneratedValue
    private Long roomId;

    private String type;
    private String roomNumber;
    private LocalDateTime timeStamp;

    public RoomEvents(EventType eventType) {
        this.type = eventType.getCleaningByUser() == null ? eventType.getType() :  eventType.getType() + " by " + eventType.getCleaningByUser();
        this.timeStamp = eventType.getTimeStamp();
        this.roomNumber = eventType.getRoomNo();
    }
}
