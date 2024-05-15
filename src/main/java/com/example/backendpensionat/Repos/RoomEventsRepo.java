package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.RoomEventHappenings.EventType;
import com.example.backendpensionat.Models.RoomEvents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomEventsRepo extends JpaRepository<RoomEvents, Long> {
    List<RoomEvents> findByRoomNumber(String roomNo);

}
