package com.example.backendpensionat.Controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void TestAllBookings() throws Exception {
        this.mockMvc.perform(get("/bookings"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("bookings")));
    }

    @Test
    void TestDeleteBookingById() throws Exception {
        long tempId = 1L;
        this.mockMvc.perform(get("/bookings/"+ tempId+"/delete")).andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }

    @Test
    void TestAddBookings() throws Exception {
        this.mockMvc.perform(get("/bookings/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<option value=\"3\">3: Karin Nilsson</option>")));
    }

    @Test
    void TestEditBookingById() throws Exception {
        long tempId = 1L;
        this.mockMvc.perform(get("/bookings/edit/" + tempId)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Edit Bookings")));
    }

    @Test
    void TestEditRefresh() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        LocalDate endDate = LocalDate.of(2024, 5, 6);
        long bookingId = 1L;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/edit/refresh")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("id", String.valueOf(bookingId)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/edit/" + bookingId));
    }


    @Test
    void TestSearchRoom() throws Exception {
        String customerInfo = "1: Anna Svensson";
        String startDate = "2024-05-01";
        String endDate = "2024-05-06";
        String roomType = "SINGLE";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/add/refresh")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("customerInfo", customerInfo)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("roomType", roomType))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/add"));
    }

    @Test
    void TestAddBooking() throws Exception{
        String customerInfo = "1: Anna Svensson";
        String startDate = "2024-05-02";
        String endDate = "2024-05-03";
        String amountOfBeds = "1";
        String roomNumber = "1";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bookings/add/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("customerInfo", customerInfo)
                        .param("startDate", startDate)
                        .param("endDate", endDate)
                        .param("amountOfBeds", amountOfBeds)
                        .param("roomNumber", roomNumber))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings"));
    }
}