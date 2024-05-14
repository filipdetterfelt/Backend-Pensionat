package com.example.backendpensionat.Models;

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
    private Long id;

    private String type;
    private String roomNumber;
    private LocalDateTime timeStamp;

    public RoomEvents(EventType eventType) {
        this.type = eventType.getEventType();
        this.timeStamp = eventType.getTimeOfEvent();
        this.roomNumber = eventType.getRoomNumber();
    }
}
