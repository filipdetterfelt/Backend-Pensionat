package com.example.backendpensionat.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.time.LocalDate;

@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void TestAllBookings() throws Exception {
    }

    @Test
    void TestDeleteBookingById() throws Exception {
        Long tempId = 1L;
        this.mockMvc.perform(get("/bookings/"+ tempId+"/delete")).andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    void TestAddBookings() throws Exception {
    }

    @Test
    void TestEditBookingById() throws Exception {
        Long tempId = 1L;
        this.mockMvc.perform(get("/bookings/"+tempId+"/edit")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Bookings")));
    }


    @Test
    void TestEditRefresh() {
    }

    @Test
    void TestSearchRoom() {
    }

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