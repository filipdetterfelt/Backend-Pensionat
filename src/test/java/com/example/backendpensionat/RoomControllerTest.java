package com.example.backendpensionat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MvcResult;








@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldShowRooms() throws Exception{
        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Rooms")));
    }

    @Test
    public void sholdShowId() throws Exception{
        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Id")));
    }

    @Test
    public void sholdShowRoomNumber() throws Exception{
        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Room number")));
    }

    @Test
    public void sholdShowTotalPrice() throws Exception{
        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Total price")));
    }

    @Test
    public void shouldShowMaxBeds() throws Exception{
        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Max beds")));
    }

    @Test
    public void shouldReeturnError() throws Exception{
        this.mockMvc.perform(get("/roomz")).andDo(print()).andExpect(status().isNotFound());
    }

   /* @Test public void shouldReturnId101() throws Exception{
        MvcResult result = this.mockMvc.perform(get("/rooms"))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Long extractedValue = JsonPath.parse(responseBody).read("$[0].id", Long.class);
        assertThat(extractedValue).isEqualTo(101L);
    }*/




}
