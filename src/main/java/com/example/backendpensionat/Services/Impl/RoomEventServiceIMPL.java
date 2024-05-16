package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.Models.RoomEventHappenings.*;
import com.example.backendpensionat.Models.RoomEvents;
import com.example.backendpensionat.Repos.RoomEventsRepo;
import com.example.backendpensionat.Services.RoomEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RoomEventServiceIMPL implements RoomEventService {

    private final RoomEventsRepo roomEventsRepo;

    public RoomEventServiceIMPL(RoomEventsRepo roomEventsRepo) {
        this.roomEventsRepo = roomEventsRepo;
    }

    @Override
    public EventType convertJSONtoEventType(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        if (message.contains("Closed")) {
            return mapper.readValue(message, DoorClosed.class);
        } else if (message.contains("CleaningFinished")) {
            return  mapper.readValue(message, CleaningEnded.class);
        } else if (message.contains("CleaningStarted")) {
            return mapper.readValue(message, CleaningStarted.class);
        } else if (message.contains("Open"))
            return mapper.readValue(message, DoorOpen.class);
        else
            return null;
    }

    @Override
    public void saveRoomEvent(String message) throws JsonProcessingException {
        EventType eventType = convertJSONtoEventType(message);
        roomEventsRepo.save(new RoomEvents(eventType));
    }
}
