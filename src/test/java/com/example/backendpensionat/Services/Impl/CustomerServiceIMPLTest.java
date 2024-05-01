package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Models.Room;
import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerServiceIMPLTest {

    //Variables for customer
    Long id = 1L;
    String firstName = "Micke";
    String lastName = "Speiner";
    String email = "micke@speiner.com";
    String phone = "0701515151";
    String Ssn = "970217-9797";
    List<Booking> BookingList = new ArrayList<>();
    List<BookingDTO> BookingListDTO = new ArrayList<>();

    //Creating customer, room, booking
    Customer customer = new Customer(id, firstName, lastName, email, phone, Ssn, BookingList);

    //Creating undeatailedCustomerDTO
    CustomerDTO smallDTO = CustomerDTO.builder().id(customer.getId()).build();

    //Creating customer
    Customer customerWithBuilder = Customer.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .phone(phone)
            .Ssn(Ssn)
            .bookings(BookingList)
            .build();

    //Creating detailedCustomerDTO
    CustomerDetailedDTO detailedCustomerDTO = CustomerDetailedDTO.builder()
            .id(id)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .phone(phone)
            .Ssn(Ssn)
            .bookings(BookingListDTO)
            .build();

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private BookingRepo bookingRepo;
    @InjectMocks
    private CustomerServiceIMPL customerServiceIMPL = new CustomerServiceIMPL(bookingRepo, customerRepo);


    @Test
    void addCustomer() {
        when(customerRepo.save(customer)).thenReturn(customer);

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
    void testCustomerToDTO() {
        CustomerDTO actaul = customerServiceIMPL.customerToDTO(customer);
        assertEquals(actaul.getId(), smallDTO.getId());

    }

    @Test
    void testCDetailedToDTO() {
        CustomerDetailedDTO actual = customerServiceIMPL.cDetailedToDTO(customer);

        assertEquals(actual.getId(), detailedCustomerDTO.getId());
        assertEquals(actual.getFirstName(), detailedCustomerDTO.getFirstName());
        assertEquals(actual.getLastName(), detailedCustomerDTO.getLastName());
        assertEquals(actual.getEmail(), detailedCustomerDTO.getEmail());
        assertEquals(actual.getPhone(), detailedCustomerDTO.getPhone());
        assertEquals(actual.getSsn(), detailedCustomerDTO.getSsn());
        assertEquals(actual.getBookings(), detailedCustomerDTO.getBookings());

    }

    @Test
    void testDetailToCustomer() {
    }

    @Test
    void findCustomerById() {
    }
}