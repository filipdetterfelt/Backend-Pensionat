package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceIMPLTest {

    Long id = 1L;
    String firstName = "Micke";
    String lastName = "Speiner";
    String email = "micke@speiner.com";
    String phone = "0701515151";
    String Ssn = "970217-9797";
    List<Booking> BookingList = new ArrayList<>();


    //Creating customer, room, booking
    Customer customer = new Customer(id, firstName, lastName, email, phone, Ssn, BookingList);
    Room room = new Room(1L, 101L, 150.0, 2, 20, BookingList);
    Booking booking = new Booking(1L, 2, 100.0, LocalDate.now(), LocalDate.now().plusDays(3), customer, room);

    //Creating undeatailedCustomerDTO
    CustomerDTO smallDTO = CustomerDTO.builder().id(customer.getId()).build();

    //Creating detailedCustomerDTO
    Customer detailedCustomerDTO = Customer.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .phone(phone)
            .Ssn(Ssn)
            .bookings(new ArrayList<>())
            .build();
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private BookingRepo bookingRepo;
    @InjectMocks
    private CustomerServiceIMPL customerServiceIMPL = new CustomerServiceIMPL(bookingRepo, customerRepo);

    @Test
    void addCustomer() {
    }

    @Test
    void changeCustomer() {
    }

    @Test
    void removeCustomer() {
    }

    @Test
    void listAllCustomers() {
    }

    @Test
    void customerToDTO() {
    }

    @Test
    void cDetailedToDTO() {
        CustomerDetailedDTO actual = customerServiceIMPL.cDetailedToDTO(customer);
    }

    @Test
    void detailToCustomer() {
    }

    @Test
    void findCustomerById() {
    }
}