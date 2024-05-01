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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        CustomerServiceIMPL service = new CustomerServiceIMPL(bookingRepo, customerRepo);

        Customer addedCustomer = service.addCustomer(detailedCustomerDTO);
        assertTrue(customer.getId().equals(addedCustomer.getId()));
    }

    @Test
    void changeCustomer() {
        when(customerRepo.save(customer)).thenReturn(customer);
        CustomerServiceIMPL service = new CustomerServiceIMPL(bookingRepo, customerRepo);
        Customer addedCustomer = service.addCustomer(detailedCustomerDTO);
        assertTrue(customer.getId().equals(addedCustomer.getId()));

    }

    @Test
    void removeCustomer() {
        CustomerServiceIMPL service = new CustomerServiceIMPL(bookingRepo, customerRepo);
        String feedback = service.removeCustomer(detailedCustomerDTO);
        assertTrue(feedback.equalsIgnoreCase("Customer has been removed"));
    }

    @Test
    void listAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceIMPL service = new CustomerServiceIMPL(bookingRepo, customerRepo);
        List<CustomerDetailedDTO> allCustomers = service.listAllCustomers();

        assertTrue(allCustomers.size() == 1);
    }

    @Test
    void testFindCustomerById() {
        when(customerRepo.findById(id)).thenReturn(Optional.of(customer));
        CustomerServiceIMPL service = new CustomerServiceIMPL(bookingRepo, customerRepo);
        CustomerDetailedDTO foundCustomer = service.findCustomerById(id);
        assertEquals(foundCustomer.getId(), customer.getId());
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
        Customer actual = customerServiceIMPL.detailToCustomer(detailedCustomerDTO);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
        assertEquals(actual.getLastName(), customer.getLastName());
        assertEquals(actual.getEmail(), customer.getEmail());
        assertEquals(actual.getPhone(), customer.getPhone());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getBookings(), customer.getBookings());
    }

}