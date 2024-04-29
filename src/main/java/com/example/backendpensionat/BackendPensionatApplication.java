package com.example.backendpensionat;

import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Repos.RoomRepo;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BackendPensionatApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPensionatApplication.class, args); }

	@Bean
	@Transactional
	public CommandLineRunner add(RoomRepo roomRepo, BookingRepo bookingRepo, CustomerRepo customerRepo) {
		return (args) -> {

			Customer customer1 = Customer.builder()
					.firstName("Anna")
					.lastName("Svensson")
					.email("anna.svensson@example.com")
					.phone("070-123 4567")
					.Ssn("19901231-1234")
					.bookings(new ArrayList<>())
					.build();

			Customer customer2 = Customer.builder()
					.firstName("Erik")
					.lastName("Johansson")
					.email("erik.johansson@example.com")
					.phone("073-987 6543")
					.Ssn("19850615-5678")
					.bookings(new ArrayList<>())
					.build();

			Customer customer3 = Customer.builder()
					.firstName("Karin")
					.lastName("Nilsson")
					.email("karin.nilsson@example.com")
					.phone("08-123 4567")
					.Ssn("19750310-7890")
					.bookings(new ArrayList<>())
					.build();

			customerRepo.saveAll(List.of(customer1, customer2, customer3));

			Room room1 = Room.builder()
					.roomNumber(101L)
					.price(150.0)
					.maxBeds(1)
					.size(20)
					.bookings(new ArrayList<>())
					.build();

			Room room2 = Room.builder()
					.roomNumber(102L)
					.price(250.0)
					.maxBeds(2)
					.size(30)
					.bookings(new ArrayList<>())
					.build();

			Room room3 = Room.builder()
					.roomNumber(103L)
					.price(350.0)
					.maxBeds(3)
					.size(45)
					.bookings(new ArrayList<>())
					.build();

			roomRepo.saveAll(List.of(room1, room2, room3));

			Booking booking1 = Booking.builder()
					.amountOfBeds(1)
					.totalPrice(500.0)
					.startDate(LocalDate.of(2024, 5, 1))
					.endDate(LocalDate.of(2024, 5, 6))
					.customer(customer1)
					.room(room1)
					.build();

			Booking booking2 = Booking.builder()
					.amountOfBeds(3)
					.totalPrice(600.0)
					.startDate(LocalDate.of(2024, 6, 10))
					.endDate(LocalDate.of(2024, 6, 13))
					.customer(customer2)
					.room(room2)
					.build();

			Booking booking3 = Booking.builder()
					.amountOfBeds(1)
					.totalPrice(500.0)
					.startDate(LocalDate.of(2024, 5, 10))
					.endDate(LocalDate.of(2024, 5, 12))
					.customer(customer3)
					.room(room3)
					.build();

			Booking booking4 = Booking.builder()
					.amountOfBeds(2)
					.totalPrice(600.0)
					.startDate(LocalDate.of(2024, 5, 13))
					.endDate(LocalDate.of(2024, 5, 16))
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
		};
	}

}
