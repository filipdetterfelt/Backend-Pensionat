package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Repos.CustomerRepo;
import com.example.backendpensionat.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@koriander.se", roles = {"Admin"})
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    Long id = 1L;
    String firstName = "Micke";
    String lastName = "Speiner";
    String email = "micke@speiner.com";
    String phone = "0701515151";
    String ssn = "970217-9797";
    List<Booking> BookingList = new ArrayList<>();
    List<BookingDTO> BookingListDTO = new ArrayList<>();

    Customer customer = new Customer(id, firstName, lastName, email, phone, ssn, BookingList);

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepo customerRepository;


    private MockHttpSession mockSession;

    @BeforeEach
    void setUp() {
        mockSession = new MockHttpSession();
        CustomerDetailedDTO customerDetailedDTO = new CustomerDetailedDTO(id, firstName, lastName, email, phone, ssn, BookingListDTO);
        mockSession.setAttribute("updatedCustomer", customerDetailedDTO);

        when(customerService.listAllCustomers()).thenReturn(List.of(customerDetailedDTO));
        when(customerService.findCustomerById(id)).thenReturn(customerDetailedDTO);
        when(customerRepository.findCustomerBySsn(ssn)).thenReturn(Optional.of(customer));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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
        CustomerDetailedDTO customerDetailedDTO = new CustomerDetailedDTO(id, firstName, lastName, email, phone, ssn, BookingListDTO);
        mockSession.setAttribute("updatedCustomer", customerDetailedDTO);

        mockMvc.perform(post("/updateCustomers")
                        .session(mockSession))
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
        long customerId = 1L;
        mockMvc.perform(get("/customers/" + customerId))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/UpdateCustomer"));
    }
}
