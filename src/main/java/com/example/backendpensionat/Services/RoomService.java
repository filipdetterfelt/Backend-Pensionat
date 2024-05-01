package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTOExpanded;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Models.Room;
import java.util.List;

public interface RoomService {
    List<RoomDetailedDTO> listAllRooms();
    List<RoomDetailedDTOExpanded> listAllRoomsExpanded();
    List<RoomDetailedDTO> listFreeRooms(RoomSearchDTO roomSearch);
    RoomDTO roomToDTO(Room room);
    RoomDetailedDTO rDetailedToDTO(Room room);
    Room detailToRoom(RoomDetailedDTO roomDetailedDTO);
    RoomDetailedDTOExpanded toExpandedDTO(Room room);
    RoomDetailedDTO findRoomNumber(Long roomNumber);
    RoomDetailedDTO findRoomById(Long id);
}
