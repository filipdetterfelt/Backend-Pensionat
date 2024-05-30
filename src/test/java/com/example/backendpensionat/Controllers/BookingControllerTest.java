package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "admin@koriander.se", roles = {"Admin"})
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private RoomRepo roomRepo;

    @MockBean
    private RoomService roomService;

    @MockBean
    private CustomerRepo customerRepo;

    @MockBean
    private BookingRepo bookingRepo;

    private MockHttpSession mockSession;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Anna")
                .lastName("Svensson")
                .email("anna@email.com")
                .phone("0701234567")
                .ssn("123456789012")
                .build();

        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(customer.getId())
                .build();

        CustomerDetailedDTO customerDetailedDTO = CustomerDetailedDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .ssn(customer.getSsn())
                .build();

        Room room = Room.builder()
                .id(1L)
                .roomNumber(101L)
                .price(150.0)
                .roomType(RoomType.SINGLE)
                .build();

        RoomDetailedDTO roomDTO = RoomDetailedDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();

        Booking booking = Booking.builder()
                .id(1L)
                .amountOfBeds(1)
                .totalPrice(150.0)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .room(room)
                .customer(customer)
                .build();

        BookingDetailedDTO bookingDTO = BookingDetailedDTO.builder()
                .id(booking.getId())
                .amountOfBeds(booking.getAmountOfBeds())
                .totalPrice(booking.getTotalPrice())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .room(roomDTO)
                .customerDTO(customerDTO)
                .build();

        customer.setBookings(List.of(booking));
        room.setBookings(List.of(booking));


        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        when(customerRepo.findById(any(Long.class))).thenReturn(Optional.of(customer));
        when(bookingRepo.findById(any(Long.class))).thenReturn(Optional.of(booking));
        when(customerService.findCustomerById(customer.getId())).thenReturn(customerDetailedDTO);
        when(bookingService.findBookingById(booking.getId())).thenReturn(bookingDTO);
        when(roomService.rDetailedToDTO(any(Room.class))).thenReturn(roomDTO);
        when(bookingService.calculateTotalPrice(any(LocalDate.class), any(LocalDate.class), any(Double.class), any(CustomerDetailedDTO.class))).thenReturn(150.0);

        mockSession = new MockHttpSession();
        mockSession.setAttribute("customer", customerDTO);
        mockSession.setAttribute("room", roomDTO);
    }

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
        this.mockMvc.perform(get("/bookings/" + tempId + "/delete"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    void TestAddBookings() throws Exception {
        this.mockMvc.perform(get("/bookings/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<label for=\"startDate\">")));
    }

    @Test
    void TestEditBookingById() throws Exception {
        long bookingId = 1L;
        int amountOfBeds = 2;
        Double totalPrice = 0.0;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2024, 5, 2);

        long customerId = 1L;
        String firstname = "Sven";
        String lastname = "Svensson";
        String mail = "sven.svensson@test.se";
        String phone = "071223231";
        String ssn = "1321532153";

        long roomId = 1L;
        long roomNumber = 101L;
        Double price = 0.0;

        List<Booking> bookingList = new ArrayList<>();

        Customer customer = new Customer(customerId, firstname, lastname, mail, phone, ssn, bookingList);
        Room room = new Room(roomId, roomNumber, price, RoomType.DOUBLE, bookingList);
        BookingDetailedDTO booking = new BookingDetailedDTO(bookingId, amountOfBeds, totalPrice, startDate, endDate, "101", RoomDetailedDTO.builder().id(roomId).roomNumber(roomNumber).price(price).roomType(RoomType.SINGLE).build(), CustomerDTO.builder().id(customerId).build());

        when(bookingService.findBookingById(any(Long.class))).thenReturn(booking);
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
                        .session(mockSession)
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
    void TestAddBooking() throws Exception {
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
                .andExpect(redirectedUrl("/sendConfirmationEmail"));
    }
}
