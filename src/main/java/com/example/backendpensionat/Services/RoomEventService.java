package com.example.backendpensionat.Services;

import com.example.backendpensionat.Models.RoomEventHappenings.EventType;
import com.example.backendpensionat.Models.RoomEvents;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RoomEventService {
    EventType convertJSONtoEventType(String message) throws JsonProcessingException;
    void saveRoomEvent(String message) throws JsonProcessingException;
    List<RoomEvents> findAllRoomEvents();
    List<RoomEvents> findRoomByRoomNumber(String roomNbr);
}
