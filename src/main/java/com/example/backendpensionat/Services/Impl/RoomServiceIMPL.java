package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Models.RoomEventHappenings.EventType;
import com.example.backendpensionat.Models.RoomEvents;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.RoomEventsRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.RoomService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
public class RoomServiceIMPL implements RoomService {

    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;
    private final BookingService bookingService;
    private final RoomEventsRepo roomEventsRepo;



    public RoomServiceIMPL(BookingRepo bookingRepo, RoomRepo roomRepo, BookingService bookingService, RoomEventsRepo roomEventsRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
        this.bookingService = bookingService;
        this.roomEventsRepo = roomEventsRepo;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @NotNull
    private List<RoomDetailedDTO> getRoomDetailedDTOS(String jpqlQuery, LocalDate startDate, LocalDate endDate, RoomType roomType) {
        return entityManager.createQuery(jpqlQuery, Room.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("roomType", roomType)
                .getResultList().stream().map(room -> RoomDetailedDTO.builder()
                        .id(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .price(room.getPrice())
                        .roomType(room.getRoomType())
                        .bookings(room.getBookings().stream().map(bookingService::bookingToDTO).toList())
                        .build())
                .toList();
    }

    @Override
    public List<RoomDetailedDTO> listAllRooms() {
        return roomRepo.findAll().stream().map(this::rDetailedToDTO).toList();
    }

    @Override
    public List<RoomDetailedDTO> listFreeRooms(RoomSearchDTO roomSearch) {
        String jpqlQuery = "SELECT r FROM Room r " +
                "WHERE r.roomType >= :roomType " +
                "AND NOT EXISTS (" +
                "SELECT b FROM Booking b " +
                "WHERE b.room = r " +
                "AND b.startDate <= :endDate " +
                "AND b.endDate >= :startDate)";

        return getRoomDetailedDTOS(jpqlQuery, roomSearch.getStartDate(), roomSearch.getEndDate(), roomSearch.getRoomType());
    }

    @Override
    public List<RoomDetailedDTO> listFreeRoomsByRoomType(BookingSearchDTO bookingSearch) {
        String jpqlQuery = "SELECT r FROM Room r " +
                "WHERE r.roomType = :roomType " +
                "AND NOT EXISTS (" +
                "SELECT b FROM Booking b " +
                "WHERE b.room = r " +
                "AND b.startDate <= :endDate " +
                "AND b.endDate >= :startDate)";

        return getRoomDetailedDTOS(jpqlQuery, bookingSearch.getStartDate(), bookingSearch.getEndDate(), bookingSearch.getRoomType());
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
                .roomType(room.getRoomType())
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
                .roomType(room.getRoomType())
                .bookings(room.getBookings()
                        .stream().map(b -> bookingRepo.findById(b.getId())
                                .orElse(null))
                        .toList()).build();
    }

    @Override
    public RoomDetailedDTO findRoomNumber(Long roomNumber) {
        Room room = roomRepo.findAll().stream().filter(r -> Objects.equals(r.getRoomNumber(), roomNumber)).findFirst().get();
        return rDetailedToDTO(room);
    }

    @Override
    public RoomDetailedDTO findRoomById(Long id) {
        Room room = roomRepo.findById(id).get();
        return rDetailedToDTO(room);
    }

    @Override
    public List<RoomEvents> findRoomEventsById(Long id){

        return roomEventsRepo.findByRoomId(id);
    }

    @Override
    public List<RoomEvents> findAllRoomEvents(){
        return roomEventsRepo.findAll();
    }

    @Override
    public List<RoomEvents> findRoomEventsByRoomnumber(String roomNbr){
        return findAllRoomEvents().stream().filter(roomEvents -> Objects.equals(roomEvents.getRoomNumber(), roomNbr))
                .toList();

    }
}
