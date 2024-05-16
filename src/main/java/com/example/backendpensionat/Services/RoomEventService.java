package com.example.backendpensionat.Services;

import com.example.backendpensionat.Models.RoomEventHappenings.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface RoomEventService {
    EventType convertJSONtoEventType(String message) throws JsonProcessingException;
    void saveRoomEvent(String message) throws JsonProcessingException;
}
