package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.Impl.BookingServiceIMPL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingService;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private BookingServiceIMPL serviceIMPL;

    @Test
    void TestAllBookings() throws Exception {
        this.mockMvc.perform(get("/bookings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("bookings")));
    }

    @Test
    void TestDeleteBookingById() throws Exception {
        long tempId = 1L;
        this.mockMvc.perform(get("/bookings/"+ tempId+"/delete")).andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    void TestAddBookings() throws Exception {
        this.mockMvc.perform(get("/bookings/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<label for=\"startDate\"></label>")));
    }

    @Test
    void TestEditBookingById() throws Exception {
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

//        List<BookingDTO> bookingDTOList = new ArrayList<>();
        List<Booking> bookingList = new ArrayList<>();

//        RoomDetailedDTO roomDTO = new RoomDetailedDTO(roomId, roomNumber, price,RoomType.DOUBLE, bookingDTOList);
//        CustomerDTO customerDTO = new CustomerDTO(customerId);

        Customer customer = new Customer(customerId,firstname,lastname,mail,phone,ssn,bookingList);
        Room room = new Room(roomId,roomNumber,price, RoomType.DOUBLE,bookingList);
        Booking booking = new Booking(bookingId,amountOfBeds,totalPrice,startDate,endDate,customer,room);

//        bookingRepo.save(booking);
        when(serviceIMPL.findBookingById(bookingId)).thenReturn(serviceIMPL.bDetailedToDTO(booking));
        this.mockMvc.perform(get("/bookings/edit/" + bookingId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Bookings")));
    }

    @Test
    void TestEditRefresh() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        LocalDate endDate = LocalDate.of(2024, 5, 6);
        long bookingId = 1L;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/edit/refresh")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("id", String.valueOf(bookingId)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/edit/" + bookingId));
    }


    @Test
    void TestSearchRoom() throws Exception {
        String customerInfo = "1: Anna Svensson";
        String startDate = "2024-05-01";
        String endDate = "2024-05-06";
        String roomType = "SINGLE";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/add/refresh")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("customerInfo", customerInfo)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("roomType", roomType))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/add"));
    }

    @Test
    void TestAddBooking() throws Exception{
        String customerInfo = "1: Anna Svensson";
        String startDate = "2024-05-02";
        String endDate = "2024-05-03";
        String amountOfBeds = "0";
        String roomNumber = "101";
        String totalPrice = "150";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/add/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("customerInfo", customerInfo)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("amountOfBeds", amountOfBeds)
                        .param("roomNumber", roomNumber)
                        .param("totalPrice", totalPrice))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }
}