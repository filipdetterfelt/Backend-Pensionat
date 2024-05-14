package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.RoomEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomEventsRepo extends JpaRepository<RoomEvents, Long> {
}
