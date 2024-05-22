package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    Long id = 1L;
    String firstName = "Micke";
    String lastName = "Speiner";
    String email = "micke@speiner.com";
    String phone = "0701515151";
    String ssn = "970217-9797";
    List<Booking> BookingList = new ArrayList<>();
    List<BookingDTO> BookingListDTO = new ArrayList<>();

    //Creating customer, room, booking
    Customer customer = new Customer(id, firstName, lastName, email, phone, ssn, BookingList);
    @Mock
    CustomerDetailedDTO CustomerdetailedCustomerDTO = new CustomerDetailedDTO();

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;


    @Test
    void testcustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("customers")));
    }

    @Test
    void testAddNewCustomer() throws Exception {
        mockMvc.perform(get("/addNewCustomer"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<label for=\"phone\">PHONE:</label><br>")));
    }

    @Test
    void testAddCustomers() throws Exception {
        mockMvc.perform(post("/addCustomer"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testUpdateCustomers() throws Exception {
        mockMvc.perform(post("/UpdateCustomers"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void removeCustomer() throws Exception {
        mockMvc.perform(post("/deleteCustomer"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
            mockMvc.perform(get("/addNewCustomer"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("<label for=\"phone\">PHONE:</label><br>")));
    }

    @Test
    void testEditCustomerById() throws Exception {
        Long customerId = 1L;
        mockMvc.perform(get("/customers/" + customerId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/UpdateCustomer"));
    }
}