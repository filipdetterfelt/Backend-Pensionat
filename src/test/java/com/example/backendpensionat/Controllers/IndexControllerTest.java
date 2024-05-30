package com.example.backendpensionat.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@koriander.se", roles = {"Admin"})
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldShowKoriander() throws Exception{
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Koriander")));
    }

    @Test
    public void shouldShowCustomer() throws Exception{
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("customer")));
    }

    @Test public void shouldShowBooking() throws Exception{
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("booking")));
    }

    @Test public void shouldReturnError() throws Exception{
        this.mockMvc.perform(get("/get404error")).andDo(print()).andExpect(status().isNotFound());
    }




}
