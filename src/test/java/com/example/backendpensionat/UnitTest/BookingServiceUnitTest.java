package com.example.backendpensionat.UnitTest;

import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import com.example.backendpensionat.Services.Impl.BookingServiceIMPL;
import com.example.backendpensionat.Services.Impl.CustomerServiceIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceUnitTest {

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private BookingServiceIMPL bookingSut;

    @InjectMocks
    private CustomerServiceIMPL customerSut;

    @BeforeEach
    void setUp() {
        Room mockedRoom = new Room(1L, 101L, RoomType.SINGLE.getRoomTypePrice(), RoomType.SINGLE, new ArrayList<>());

        Customer customerWithoutBookings = new Customer(1L, "Sven", "Svensson", "sven.svensson@hotmail.com", "0712345678", "199505036234", new ArrayList<>());
        Customer customerTenDaysBooked = new Customer(2L, "Kalle", "Karlsson", "kalle.karlsson@email.com", "0712345679", "199505036235", new ArrayList<>());

        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), customerTenDaysBooked, mockedRoom);

        mockedRoom.getBookings().add(bookingTenDays);
        customerTenDaysBooked.getBookings().add(bookingTenDays);

        when(roomRepo.findById(1L)).thenReturn(Optional.of(mockedRoom));
        when(customerRepo.findById(Mockito.anyLong())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1L)) {
                return Optional.of(customerWithoutBookings);
            } else if (invocation.getArgument(0).equals(2L)) {
                return Optional.of(customerTenDaysBooked);
            }
            return Optional.empty();
        });
    }

    @Test
    void calculateTotalPrice_oneDayBooked_ShouldGiveNoDiscount() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).get());
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        Room room = roomRepo.findById(1L).get();

        double totalPrice = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(1 * RoomType.SINGLE.getRoomTypePrice(), totalPrice);
    }

    @Test
    void calculateTotalPrice_twoDaysBooked_shouldGiveDiscountHalfPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).get());
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 3);
        double totalPrice = 2 * RoomType.SINGLE.getRoomTypePrice();
        Room room = roomRepo.findById(1L).get();

        double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(totalPrice * 0.995, result);
    }

    @Test
    void calculateTotalPrice_customerWithTenDaysBooked_ShouldGiveDiscountTwoPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(2L).get());
        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), new Customer(), new Room());
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(bookingTenDays));

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        double expectedPrice = (1 * RoomType.SINGLE.getRoomTypePrice()) * 0.98;
        Room room = roomRepo.findById(1L).get();

        double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
    }

    @Test
    void calculateTotalPrice_customerWithTenDaysBooked_TwoDays_ShouldGiveDiscountTwoAndHalfPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(2L).get());
        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), new Customer(), new Room());
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(bookingTenDays));

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 3);
        double expectedPrice = (2 * RoomType.SINGLE.getRoomTypePrice()) * 0.98 * 0.995;
        Room room = roomRepo.findById(1L).get();

        double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
    }

    @Test
    void calculateTotalPrice_customerWithTenDaysBooked_TwoDaysWithOneDayOverSunday_ShouldGiveDiscountTwoPercentPlusDiscountedDay() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(2L).get());
        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), new Customer(), new Room());
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(bookingTenDays));

        LocalDate startDate = LocalDate.of(2024, 1, 6);
        LocalDate endDate = LocalDate.of(2024, 1, 8);
        double expectedPrice = (1 * RoomType.SINGLE.getRoomTypePrice() + (1 * RoomType.SINGLE.getRoomTypePrice() * 0.98))  * 0.98 * 0.995;
        Room room = roomRepo.findById(1L).get();

        double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
    }
}
