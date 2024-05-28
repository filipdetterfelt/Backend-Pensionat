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
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).orElseThrow(() -> noSuchElementException("Customer")));
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        double expectedPrice = 1 * RoomType.SINGLE.getRoomTypePrice();
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    @Test
    void calculateTotalPrice_twoDaysBooked_shouldGiveDiscountHalfPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).orElseThrow(() -> noSuchElementException("Customer")));
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 3);

        double expectedPrice = 2 * RoomType.SINGLE.getRoomTypePrice();
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice * 0.995, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    @Test
    void calculateTotalPrice_oneDayBookedOverSunday_ShouldGiveDiscountTwoPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).orElseThrow(() -> noSuchElementException("Customer")));
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        LocalDate startDate = LocalDate.of(2024, 1, 7);
        LocalDate endDate = LocalDate.of(2024, 1, 8);

        double expectedPrice = 1 * RoomType.SINGLE.getRoomTypePrice() * 0.98;
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    @Test
    void calculateTotalPrice_twoDaysBookedOverSunday_ShouldGiveDiscountTwoAndHalfPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).orElseThrow(() -> noSuchElementException("Customer")));
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        LocalDate startDate = LocalDate.of(2024, 1, 6);
        LocalDate endDate = LocalDate.of(2024, 1, 8);

        double expectedPrice = ((1 * RoomType.SINGLE.getRoomTypePrice() * 0.98) + (1 * RoomType.SINGLE.getRoomTypePrice())) * 0.995;
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    @Test
    void calculateTotalPrice_oneDayBookedEndsOnSunday_shouldGiveNoDiscount() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(1L).orElseThrow(() -> noSuchElementException("Customer")));
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        LocalDate startDate = LocalDate.of(2024, 1, 6);
        LocalDate endDate = LocalDate.of(2024, 1, 7);

        double expectedPrice = 1 * RoomType.SINGLE.getRoomTypePrice();
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    @Test
    void calculateTotalPrice_customerWithTenDaysBooked_ShouldGiveDiscountTwoPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(2L).orElseThrow(() -> noSuchElementException("Customer")));
        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), new Customer(), new Room());
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(bookingTenDays));

        LocalDate startDate = LocalDate.of(2024, 1, 6);
        LocalDate endDate = LocalDate.of(2024, 1, 7);

        double expectedPrice = (1 * RoomType.SINGLE.getRoomTypePrice()) * 0.98;
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }


    @Test
    void calculateTotalPrice_customerWithTenDaysBooked_TwoDays_ShouldGiveDiscountTwoAndHalfPercent() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(2L).orElseThrow(() -> noSuchElementException("Customer")));
        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), new Customer(), new Room());
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(bookingTenDays));

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 3);

        double expectedPrice = (2 * RoomType.SINGLE.getRoomTypePrice()) * 0.975;
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    @Test
    void calculateTotalPrice_customerWithTenDaysBooked_TwoDaysWithOneDayOverSunday_ShouldGiveDiscountTwoAndHalfPercentPlusDiscountedDay() {
        CustomerDetailedDTO customer = customerSut.cDetailedToDTO(customerRepo.findById(2L).orElseThrow(() -> noSuchElementException("Customer")));
        Booking bookingTenDays = new Booking(1L, 0, 10 * RoomType.SINGLE.getRoomTypePrice(), LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 11), new Customer(), new Room());
        Room room = roomRepo.findById(1L).orElseThrow(() -> noSuchElementException("Room"));

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(bookingTenDays));

        LocalDate startDate = LocalDate.of(2024, 1, 6);
        LocalDate endDate = LocalDate.of(2024, 1, 8);

        double expectedPrice = (1 * RoomType.SINGLE.getRoomTypePrice() + (1 * RoomType.SINGLE.getRoomTypePrice() * 0.98))  * 0.975;
        Double result = bookingSut.calculateTotalPrice(startDate, endDate, room.getPrice(), customer);

        assertEquals(expectedPrice, result);
        assertEquals(room.getPrice(), RoomType.SINGLE.getRoomTypePrice());
        assertNotNull(result);
    }

    private NoSuchElementException noSuchElementException(String entityName) {
        return new NoSuchElementException(entityName + " not found.");
    }
}
