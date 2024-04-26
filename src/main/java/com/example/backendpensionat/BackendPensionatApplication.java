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
import java.util.Arrays;
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
					.id(1L)
					.firstName("Anna")
					.lastName("Svensson")
					.email("anna.svensson@example.com")
					.phone("070-123 4567")
					.Ssn("19901231-1234")
					.bookings(new ArrayList<>()) // En tom lista för bokningar
					.build();

			Customer customer2 = Customer.builder()
					.id(2L)
					.firstName("Erik")
					.lastName("Johansson")
					.email("erik.johansson@example.com")
					.phone("073-987 6543")
					.Ssn("19850615-5678")
					.bookings(new ArrayList<>()) // En tom lista för bokningar
					.build();

			Customer customer3 = Customer.builder()
					.id(3L)
					.firstName("Karin")
					.lastName("Nilsson")
					.email("karin.nilsson@example.com")
					.phone("08-123 4567")
					.Ssn("19750310-7890")
					.bookings(new ArrayList<>()) // En tom lista för bokningar
					.build();

			Room room1 = Room.builder()
					.id(1L)
					.roomNumber(101L)
					.price(150.0)
					.size(20)
					.bookings(new ArrayList<>()) // En tom lista för bokningar
					.build();

			Room room2 = Room.builder()
					.id(2L)
					.roomNumber(202L)
					.price(250.0)
					.size(30)
					.bookings(new ArrayList<>()) // En tom lista för bokningar
					.build();

			Booking booking1 = Booking.builder()
					.Id(1L)
					.amount(5)
					.totalPrice(500.0)
					.startDate(LocalDate.of(2024, 5, 1))
					.endDate(LocalDate.of(2024, 5, 6))
					.customer(customer1)
					.room(room1)
					.build();

			Booking booking2 = Booking.builder()
					.Id(2L)
					.amount(3)
					.totalPrice(600.0)
					.startDate(LocalDate.of(2024, 6, 10))
					.endDate(LocalDate.of(2024, 6, 13))
					.customer(customer2)
					.room(room2)
					.build();



			//room1.getBookings().add(booking1);
			//room2.getBookings().add(booking2);

			bookingRepo.saveAll(Arrays.asList(booking1,booking2));
			//customer1.getBookings().add(booking1);
			//customer2.getBookings().add(booking2);

			//customerRepo.saveAll(Arrays.asList(customer1,customer2));

		};
	}

}
