package com.example.backendpensionat.Services.Impl;

import com.example.backendpensionat.Repos.BookingRepo;
import com.example.backendpensionat.Repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceIMPLTest {

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
    }

    @Test
    void detailToCustomer() {
    }

    @Test
    void findCustomerById() {
    }
}