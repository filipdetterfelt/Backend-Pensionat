package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BookingDetailedDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void TestAllBookings() throws Exception {
    }

    //Filip klar
    @Test
    void TestDeleteBookingById() throws Exception {
        Long tempId = 1L;
        this.mockMvc.perform(get("/bookings/"+ tempId+"/delete")).andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    void TestAddBookings() throws Exception {
    }

    //Filip Klar
    @Test
    void TestEditBookingById() throws Exception {
        Long tempId = 1L;
        this.mockMvc.perform(get("/bookings/"+tempId+"/edit")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Bookings")));
    }


    @Test
    void TestEditRefresh() {
    }

    //Filip
    @Test
    void TestUpdateBookingPost() throws Exception {
        BookingDetailedDTO bookingDetailedDTO = new BookingDetailedDTO();
        mockMvc.perform(post("/bookings/update")).andDo(print())
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/bookings"));

    }

    @Test
    void TestSearchRoom() {
    }

    //Filip
    @Test
    void TestAddBooking() throws Exception{
        Long customerId = 1L;
        LocalDate startDate = LocalDate.of(2024,5,2);
        LocalDate endDate = LocalDate.of(2024,5,3);
        int extraBeds = 1;
        int roomNumber = 2;
        this.mockMvc.perform(get("/bookings/add/{customerId}/{startDate}/{endDate}/{extraBeds}/{roomNo}"
                        , customerId,startDate,endDate,extraBeds,roomNumber))
                .andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }


}