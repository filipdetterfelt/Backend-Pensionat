package com.example.backendpensionat.IntegrationTest;

import com.example.backendpensionat.Models.RoomEventHappenings.*;
import com.example.backendpensionat.Services.RoomEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class RoomEventServiceIntegrationTest {


    @Autowired
    RoomEventService sut;

    @Test
    public void getRoomEventsFromJson() throws IOException {
        ClassPathResource resource = new ClassPathResource("XmlJsonFiles/roomEvent.json");
        List<String> messages = Arrays.stream(new String(Files.readAllBytes(Paths.get(resource.getURI())))
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .split("},"))
                .map(string -> string.concat("}"))
                .toList();

        EventType roomOpened = sut.convertJSONtoEventType(messages.get(0));
        EventType roomClosed = sut.convertJSONtoEventType(messages.get(1));
        EventType cleaningStarted = sut.convertJSONtoEventType(messages.get(2));
        EventType cleaningFinished = sut.convertJSONtoEventType(messages.get(3));

        assertInstanceOf(DoorOpen.class, roomOpened);
        assertInstanceOf(DoorClosed.class, roomClosed);
        assertInstanceOf(CleaningStarted.class, cleaningStarted);
        assertInstanceOf(CleaningEnded.class, cleaningFinished);

    }
}
