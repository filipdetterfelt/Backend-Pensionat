package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookingsServiceImplTest {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private CustomerRepo customerRepo;


    @InjectMocks
    private BookingServiceIMPL serviceIMPL = new BookingServiceIMPL(customerRepo , bookingRepo, roomRepo);

    long bookingId = 1L;
    int amountOfBeds = 2;
    Double totalPrice = 0.0;
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
    Double price = 0.0;

    List<BookingDTO> bookingDTOList = new ArrayList<>();
    List<Booking> bookingList = new ArrayList<>();

    private RoomDetailedDTO roomDTO = new RoomDetailedDTO(roomId, roomNumber, price,RoomType.DOUBLE, bookingDTOList);
    private final CustomerDTO customerDTO = new CustomerDTO(customerId);

    Customer customer = new Customer(customerId,firstname,lastname,mail,phone,ssn,bookingList);
    Room room = new Room(roomId,roomNumber,price, RoomType.DOUBLE,bookingList);
    Booking booking = new Booking(bookingId,amountOfBeds,totalPrice,startDate,endDate,customer,room);

    BookingDetailedDTO bookingDetailedDTO = BookingDetailedDTO.builder().id(bookingId)
            .amountOfBeds(amountOfBeds).totalPrice(totalPrice).startDate(startDate).endDate(endDate).customerDTO(customerDTO).room(roomDTO).build();




    @Test
    void bookingToDto(){
        BookingDTO actual = serviceIMPL.bookingToDTO(booking);
        assertEquals(actual.getId(),booking.getId());
    }

    @Test
    void bookingDetailedToDto(){
        BookingDetailedDTO actual = serviceIMPL.bDetailedToDTO(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getAmountOfBeds(),booking.getAmountOfBeds());
        assertEquals(actual.getTotalPrice(),booking.getTotalPrice());
        assertEquals(actual.getStartDate(),booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getRoom().getId(),booking.getRoom().getId());
        assertEquals(actual.getCustomerDTO().getId(),booking.getCustomer().getId());
    }

    /*@Override
    public Booking detailToBooking(BookingDetailedDTO bookDTO) {

        return Booking.builder().Id(bookDTO.getId())
                .amountOfBeds(bookDTO.getAmountOfBeds())
                .totalPrice(bookDTO.getTotalPrice())
                .startDate(bookDTO.getStartDate())
                .endDate(bookDTO.getEndDate())
                .customer(customerRepo.findById(bookDTO.getCustomerDTO().getId()).orElse(null))
                .room(roomRepo.findById(bookDTO.getRoomDTO().getId()).orElse(null)).build();
    }*/
    @Test
    void bookingDetailedToBooking(){
        BookingServiceIMPL service2 = new BookingServiceIMPL(customerRepo , bookingRepo, roomRepo);
        when(roomRepo.findById(roomId)).thenReturn(Optional.of(room));
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        Booking actual = service2.detailToBooking(bookingDetailedDTO);
           assertEquals(actual.getId(),booking.getId());
           assertEquals(actual.getAmountOfBeds(), booking.getAmountOfBeds());
           assertEquals(actual.getTotalPrice(),booking.getTotalPrice());
           assertEquals(actual.getStartDate(),booking.getStartDate());
           assertEquals(actual.getEndDate(),booking.getEndDate());
           assertEquals(actual.getRoom(),booking.getRoom());
           assertEquals(actual.getCustomer(), booking.getCustomer());
    }

    @Test
    void showAllBooking() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceIMPL bookingServiceIMPL2 = new BookingServiceIMPL(customerRepo,bookingRepo,roomRepo);
        List<BookingDetailedDTO> allBookings = bookingServiceIMPL2.listAllBookings();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
