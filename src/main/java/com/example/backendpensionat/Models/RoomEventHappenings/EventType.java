package com.example.backendpensionat.Models.RoomEventHappenings;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class EventType {
    LocalDateTime timeStamp;
    String type;
    String roomNo;

    public EventType(LocalDateTime timeStamp, String type, String roomNo) {
        this.timeStamp = timeStamp;
        this.type = type;
        this.roomNo = roomNo;
    }

    public EventType() {
    }
}
