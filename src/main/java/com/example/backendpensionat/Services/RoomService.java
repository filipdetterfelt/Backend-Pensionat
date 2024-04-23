package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.Models.Room;

public interface RoomService {
    RoomDTO roomToDTO(Room room);
    RoomDetailedDTO rDetailedToDTO(Room room);
    Room detailToRoom(RoomDetailedDTO roomDetailedDTO);
}
