package com.example.backendpensionat;

import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ComponentScan
@RequiredArgsConstructor
public class SyncMockData implements CommandLineRunner {

    private final RoomRepo roomRepo;
    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;

    @Override
    public void run(String... args) throws Exception {

        if (roomRepo.count() > 0 && bookingRepo.count() > 0 && customerRepo.count() > 0) {
            return;
        }

            Customer customer1 = Customer.builder()
                    .firstName("Anna")
                    .lastName("Svensson")
                    .email("anna.svensson@example.com")
                    .phone("0701234567")
                    .Ssn("199012311234")
                    .bookings(new ArrayList<>())
                    .build();

            Customer customer2 = Customer.builder()
                    .firstName("Erik")
                    .lastName("Johansson")
                    .email("erik.johansson@example.com")
                    .phone("0739876543")
                    .Ssn("198506155678")
                    .bookings(new ArrayList<>())
                    .build();

            Customer customer3 = Customer.builder()
                    .firstName("Karin")
                    .lastName("Nilsson")
                    .email("karin.nilsson@example.com")
                    .phone("081234567")
                    .Ssn("197503107890")
                    .bookings(new ArrayList<>())
                    .build();

            customerRepo.saveAll(List.of(customer1, customer2, customer3));

            Room room1 = Room.builder()
                    .roomNumber(101L)
                    .price(RoomType.getRoomType(0).getRoomTypePrice())
                    .roomType(RoomType.SINGLE)
                    .bookings(new ArrayList<>())
                    .build();

            Room room2 = Room.builder()
                    .roomNumber(102L)
                    .price(RoomType.getRoomType(1).getRoomTypePrice())
                    .roomType(RoomType.DOUBLE)
                    .bookings(new ArrayList<>())
                    .build();

            Room room3 = Room.builder()
                    .roomNumber(103L)
                    .price(RoomType.getRoomType(2).getRoomTypePrice())
                    .roomType(RoomType.SUITE)
                    .bookings(new ArrayList<>())
                    .build();

            roomRepo.saveAll(List.of(room1, room2, room3));

            Booking booking1 = Booking.builder()
                    .amountOfBeds(0)
                    .startDate(LocalDate.of(2024, 5, 1))
                    .endDate(LocalDate.of(2024, 5, 6))
                    .totalPrice(RoomType.getRoomType(0).getRoomTypePrice()*5)
                    .customer(customer1)
                    .room(room1)
                    .build();

            Booking booking2 = Booking.builder()
                    .amountOfBeds(1)
                    .startDate(LocalDate.of(2024, 6, 10))
                    .endDate(LocalDate.of(2024, 6, 13))
                    .totalPrice(RoomType.getRoomType(1).getRoomTypePrice()*3)
                    .customer(customer2)
                    .room(room2)
                    .build();

            Booking booking3 = Booking.builder()
                    .amountOfBeds(2)
                    .startDate(LocalDate.of(2024, 5, 10))
                    .endDate(LocalDate.of(2024, 5, 12))
                    .totalPrice(RoomType.getRoomType(2).getRoomTypePrice()*2)
                    .customer(customer3)
                    .room(room3)
                    .build();

            Booking booking4 = Booking.builder()
                    .amountOfBeds(2)
                    .startDate(LocalDate.of(2024, 5, 13))
                    .endDate(LocalDate.of(2024, 5, 16))
                    .totalPrice(RoomType.getRoomType(2).getRoomTypePrice()*3)
                    .customer(customer2)
                    .room(room3)
                    .build();

            customer1.getBookings().add(booking1);
            customer2.getBookings().add(booking2);
            customer2.getBookings().add(booking4);
            customer3.getBookings().add(booking3);
            room1.getBookings().add(booking1);
            room2.getBookings().add(booking2);
            room3.getBookings().add(booking4);

            bookingRepo.saveAll(List.of(booking1, booking2, booking3, booking4));
    }
}
