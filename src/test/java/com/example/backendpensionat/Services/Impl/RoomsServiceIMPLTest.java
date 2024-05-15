package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.RoomEventsRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
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

    @Mock
    RoomEventsRepo roomEventsRepo;

    @InjectMocks
    private RoomServiceIMPL serviceIMPL = new RoomServiceIMPL(bookingRepo,roomRepo,bookingService,roomEventsRepo);

    long bookingId = 1L;
    int amountOfBeds = 2;
    double totalPrice = 1000;
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.of(2024,5,2);

    long customerId = 1L;
    String firstname = "Sven";
    String lastname = "Svensson";
    String mail = "sven.svensson@test.se";
    String phone = "071223231";
    String ssn = "1321532153";

    long roomId = 1L;
    long roomNumber = roomId;
    double price = 1000;
    RoomDTO roomDTO;
    List<BookingDTO> bookingDTOList = new ArrayList<>();
    List<Booking> bookingList = new ArrayList<>();
    CustomerDTO customerDTO;


    Customer customer = new Customer(customerId,firstname,lastname,mail,phone,ssn,bookingList);
    Room room = new Room(roomId,roomNumber,price, RoomType.DOUBLE,bookingList);
    Booking booking = new Booking(bookingId,amountOfBeds,totalPrice,startDate,endDate,customer,room);

    BookingDetailedDTO bookingDetailedDTO = BookingDetailedDTO.builder().id(bookingId)
            .amountOfBeds(amountOfBeds).totalPrice(totalPrice).startDate(startDate).endDate(endDate).build();

    RoomDetailedDTO roomDetailedDTO = new RoomDetailedDTO(roomId,roomNumber,price,RoomType.DOUBLE,bookingDTOList);

    BookingDTO bookingDTO1 = new BookingDTO(1L);
    BookingDTO bookingDTO2 = new BookingDTO(2L);
    BookingDTO bookingDTO3 = new BookingDTO(3L);

    //BookingDetailedDTO bookingDetailedDTO1 = new BookingDetailedDTO(bookingId,amountOfBeds,totalPrice,startDate,endDate,"",roomDetailedDTO,customerDTO);


    //List<BookingDTO> bookingDTOList = Arrays.asList(bookingDTO1,bookingDTO2,bookingDTO3);
    //RoomDetailedDTO roomDetailedDTO = RoomDetailedDTO.builder().id


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
        RoomServiceIMPL roomServiceIMPL2 = new RoomServiceIMPL(bookingRepo,roomRepo,bookingService,roomEventsRepo);
        List<RoomDetailedDTO> allRooms = roomServiceIMPL2.listAllRooms();
    }



}
