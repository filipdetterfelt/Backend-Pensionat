package com.example.backendpensionat.UnitTest;

import com.example.backendpensionat.DTO.ShippersDetailedDTO;
import com.example.backendpensionat.Models.RoomEventHappenings.*;
import com.example.backendpensionat.Models.RoomEvents;
import com.example.backendpensionat.Models.Shippers;
import com.example.backendpensionat.Repos.RoomEventsRepo;
import com.example.backendpensionat.Repos.ShippersRepo;
import com.example.backendpensionat.Services.Impl.RoomEventServiceIMPL;
import com.example.backendpensionat.Services.Impl.ShippersServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class RoomEventServiceUnitTest {
    private final RoomEventsRepo roomEventsRepo = mock(RoomEventsRepo.class);
    URL localUrl = getClass().getClassLoader().getResource("./XmlJsonFiles/roomEvents.json");
    RoomEventServiceIMPL sut;

    @BeforeEach()
    void setUp() {
        sut = new RoomEventServiceIMPL(roomEventsRepo);
    }

    @Test
    void ValidJSON_ConvertJSONtoEventType() throws IOException {

        String message1 = "{\"roomNo\":\"101\",\"type\":\"DoorClosed\",\"timeStamp\":\"2021-09-01T12:00:00\"}";
        String message2 = "{\"roomNo\":\"101\",\"type\":\"DoorOpen\",\"timeStamp\":\"2021-09-01T12:00:00\"}";
        String message3 = "{\"roomNo\":\"101\",\"type\":\"CleaningStarted\",\"timeStamp\":\"2021-09-01T12:00:00\", \"cleaningByUser\":\"Erik\"}";
        String message4 = "{\"roomNo\":\"101\",\"type\":\"CleaningFinished\",\"timeStamp\":\"2021-09-01T12:00:00\", \"cleaningByUser\":\"Erik\"}";

        EventType resultMessage1 = sut.convertJSONtoEventType(message1);
        EventType resultMessage2 = sut.convertJSONtoEventType(message2);
        EventType resultMessage3 = sut.convertJSONtoEventType(message3);
        EventType resultMessage4 = sut.convertJSONtoEventType(message4);

        assertEquals("101", resultMessage1.getRoomNo());
        assertEquals("DoorClosed", resultMessage1.getType());
        assertEquals("2021-09-01T12:00", resultMessage1.getTimeStamp().toString());
        assertInstanceOf(DoorClosed.class, resultMessage1);

        assertEquals("101", resultMessage2.getRoomNo());
        assertEquals("DoorOpen", resultMessage2.getType());
        assertEquals("2021-09-01T12:00", resultMessage2.getTimeStamp().toString());
        assertInstanceOf(DoorOpen.class, resultMessage2);

        assertEquals("101", resultMessage3.getRoomNo());
        assertEquals("CleaningStarted", resultMessage3.getType());
        assertEquals("2021-09-01T12:00", resultMessage3.getTimeStamp().toString());
        assertEquals("Erik", resultMessage3.getCleaningByUser());
        assertInstanceOf(CleaningStarted.class, resultMessage3);

        assertEquals("101", resultMessage4.getRoomNo());
        assertEquals("CleaningFinished", resultMessage4.getType());
        assertEquals("2021-09-01T12:00", resultMessage4.getTimeStamp().toString());
        assertEquals("Erik", resultMessage4.getCleaningByUser());
        assertInstanceOf(CleaningEnded.class, resultMessage4);
    }

    @Test
    void invalidJSON_ConvertJSONtoEventType() throws IOException {
        String message1 = "{\"roomNo\":\"101\",\"type\":\"InvalidType\",\"timeStamp\":\"2021-09-01T12:00:00\"}";
        String message2 = "{\"invalid\":\"101\",\"invalid\":\"DoorClosed\",\"invalid\":\"2021-09-01T12:00:00\"}";

        EventType resultMessage = sut.convertJSONtoEventType(message1);
        assertNull(resultMessage);
        assertThrows(IOException.class, () -> sut.convertJSONtoEventType(message2));
    }
}
