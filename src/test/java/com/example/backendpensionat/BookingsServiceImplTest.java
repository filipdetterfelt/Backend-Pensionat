package com.example.backendpensionat;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.RoomDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.Impl.BookingServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookingsServiceImplTest {

    @Mock
    BookingRepo bookingRepo;

    @Mock
    RoomRepo roomRepo;

    @Mock
    CustomerRepo customerRepo;

    @InjectMocks
    private BookingServiceIMPL serviceIMPL = new BookingServiceIMPL(customerRepo , bookingRepo, roomRepo);

    long bookingId = 1L;
    int amountOfBeds = 2;
    double totalPrice = 1000;
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.of(2024,5,02);

    long customerId = 1L;
    String firstname = "Sven";
    String lastname = "Svensson";
    String mail = "sven.svensson@test.se";
    String phone = "071223231";
    String ssn = "1321532153";

    long roomId = 1L;
    long roomNumber = roomId;
    double price = 1000;
    int maxBeds = 2;
    int size = 50;


    Customer customer = new Customer(customerId,firstname,lastname,mail,phone,ssn,null);
    Room room = new Room(roomId,roomNumber,price,maxBeds,size,null);
    Booking booking = new Booking(bookingId,amountOfBeds,totalPrice,startDate,endDate,customer,room);
    BookingDetailedDTO detailedBooking = new BookingDetailedDTO(bookingId,amountOfBeds,totalPrice,startDate,endDate,null,null);



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
        assertEquals(actual.getRoomDTO().getId(),booking.getRoom().getId());
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
        Booking actual =serviceIMPL.detailToBooking(detailedBooking);

    }

    @Test
    void showAllBooking() {
        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceIMPL bookingServiceIMPL2 = new BookingServiceIMPL(customerRepo,bookingRepo,roomRepo);
        List<BookingDetailedDTO> allBookings = bookingServiceIMPL2.listAllBookings();
    }

}
