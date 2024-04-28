package com.example.backendpensionat.Services;

import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.Models.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    List<RoomDetailedDTO> listAllRooms();
    List<RoomDetailedDTO> listFreeRooms(LocalDate startDate, LocalDate endDate, int maxBeds);
    boolean isDateWithinBookingPeriod(LocalDate date, BookingDetailedDTO booking);
    RoomDTO roomToDTO(Room room);
    RoomDetailedDTO rDetailedToDTO(Room room);
    Room detailToRoom(RoomDetailedDTO roomDetailedDTO);
}
