package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
    Room findByRoomNumber(Long roomNumber);
}
