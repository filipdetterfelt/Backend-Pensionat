package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.RoomService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceIMPL implements RoomService {

    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;
    private final BookingService bookingService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoomDetailedDTO> listAllRooms() {
        return roomRepo.findAll().stream().map(this::rDetailedToDTO).toList();
    }

    @Override
    public List<RoomDetailedDTO> listFreeRooms(RoomSearchDTO roomSearch) {
        String jpqlQuery = "SELECT r FROM Room r " +
                "WHERE r.maxBeds >= :maxBeds " +
                "AND NOT EXISTS (" +
                "SELECT b FROM Booking b " +
                "WHERE b.room = r " +
                "AND b.startDate <= :endDate " +
                "AND b.endDate >= :startDate)";


        return entityManager.createQuery(jpqlQuery, Room.class)
                .setParameter("startDate", roomSearch.getStartDate())
                .setParameter("endDate", roomSearch.getEndDate())
                .setParameter("maxBeds", roomSearch.getMaxBeds())
                .getResultList().stream().map(room -> RoomDetailedDTO.builder()
                        .id(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .maxBeds(room.getMaxBeds())
                        .price(room.getPrice())
                        .size(room.getSize())
                        .bookings(room.getBookings().stream().map(bookingService::bookingToDTO).toList())
                        .build())
                .toList();
    }

    @Override
    public RoomDTO roomToDTO(Room room) {
        return RoomDTO.builder().id(room.getId()).build();
    }

    @Override
    public RoomDetailedDTO rDetailedToDTO(Room room) {
        return RoomDetailedDTO.builder().id(room.getId())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .maxBeds(room.getMaxBeds())
                .size(room.getSize())
                .bookings(room.getBookings()
                        .stream().map(b -> BookingDTO.builder()
                                .id(b.getId()).build())
                        .toList()).build();
    }

    @Override
    public Room detailToRoom(RoomDetailedDTO room) {
        return Room.builder().id(room.getId())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .maxBeds(room.getMaxBeds())
                .size(room.getSize())
                .bookings(room.getBookings()
                        .stream().map(b -> bookingRepo.findById(b.getId())
                                .orElse(null))
                        .toList()).build();
    }
}
