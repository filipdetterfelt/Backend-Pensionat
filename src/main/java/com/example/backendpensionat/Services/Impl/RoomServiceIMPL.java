package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceIMPL implements RoomService {

    private final BookingRepo bookingRepo;
    private final RoomRepo roomRepo;
    private final BookingService bookingService;

    @Override
    public List<RoomDetailedDTO> listAllRooms() {
        return roomRepo.findAll().stream().map(this::rDetailedToDTO).toList();
    }

    @Override
    public List<RoomDetailedDTO> listFreeRooms(LocalDate startDate, LocalDate endDate, int maxBeds  ) {
        LocalDate[] dates = sortDates(startDate, endDate);
        return listAllRooms().stream().filter(room -> {
            List<BookingDetailedDTO> bookings = room.getBookings().stream()
                    .map(booking -> bookingService.bDetailedToDTO(bookingRepo.findById(booking.getId()).get()))
                    .toList();

                return bookings.stream().noneMatch(booking ->
                       isDateWithinBookingPeriod(dates[0], booking) &&
                       isDateWithinBookingPeriod(dates[1], booking) &&
                       maxBeds <= room.getMaxBeds() + 1);
        }).toList();
    }

    @Override
    public LocalDate[] sortDates(LocalDate date1, LocalDate date2) {
        if (date1.isBefore(date2)) return new LocalDate[]{date1, date2};
        else if (date2.isBefore(date1)) return new LocalDate[]{date2, date1};
        else return new LocalDate[]{date1, date2};
    }

    @Override
    public boolean isDateWithinBookingPeriod(LocalDate date, BookingDetailedDTO booking) {
        return (date.isEqual(booking.getStartDate()) || date.isAfter(booking.getStartDate())) &&
               (date.isEqual(booking.getEndDate()) || date.isBefore(booking.getEndDate()));
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
