package com.example.backendpensionat;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.Impl.RoomServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RoomsServiceIMPLTest {

    @Mock
    RoomRepo roomRepo;

    @Mock
    BookingRepo bookingRepo;

    @Mock
    BookingService bookingService;

    @InjectMocks
    private RoomServiceIMPL serviceIMPL = new RoomServiceIMPL(bookingRepo,roomRepo,bookingService);



    long roomId = 1L;
    long roomNumber = roomId;
    double price = 1000;

    List<BookingDTO> bookingDTOList = new ArrayList<>();
    List<Booking> bookingList = new ArrayList<>();

    Room room = new Room(roomId,roomNumber,price, RoomType.DOUBLE,bookingList);


    RoomDetailedDTO roomDetailedDTO = new RoomDetailedDTO(roomId,roomNumber,price,RoomType.DOUBLE,bookingDTOList);


    @Test
    void roomToDTO(){
        RoomDTO actual = serviceIMPL.roomToDTO(room);
        assertEquals(actual.getId(),room.getId());
    }

    @Test
    void detailToRoom(){
        Room actual = serviceIMPL.detailToRoom(roomDetailedDTO);
        assertEquals(actual.getId(),room.getId());
        assertEquals(actual.getRoomNumber(),room.getRoomNumber());
        assertEquals(actual.getPrice(),room.getPrice());
        assertEquals(actual.getRoomType(),room.getRoomType());
        assertEquals(actual.getBookings(),room.getBookings());
    }

    @Test
    void detaliedToDTO(){
        RoomDetailedDTO actual = serviceIMPL.rDetailedToDTO(room);
        assertEquals(actual.getId(),roomDetailedDTO.getId());
        assertEquals(actual.getRoomNumber(),roomDetailedDTO.getRoomNumber());
        assertEquals(actual.getPrice(),roomDetailedDTO.getPrice());
        assertEquals(actual.getRoomType(),roomDetailedDTO.getRoomType());
        assertEquals(actual.getBookings(),roomDetailedDTO.getBookings());

    }

    @Test
    void showAllRooms(){
        when(roomRepo.findAll()).thenReturn(Arrays.asList(room));
        RoomServiceIMPL roomServiceIMPL2 = new RoomServiceIMPL(bookingRepo,roomRepo,bookingService);
        List<RoomDetailedDTO> allRooms = roomServiceIMPL2.listAllRooms();
    }



}
