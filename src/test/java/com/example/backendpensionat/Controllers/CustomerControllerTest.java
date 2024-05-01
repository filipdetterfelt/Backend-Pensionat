package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;


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
                .andExpect(content().string(containsString("<form action=\"/addCustomer\" method=\"post\">")));
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
                    .andExpect(content().string(containsString("<form action=\"/addCustomer\" method=\"post\">")));
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