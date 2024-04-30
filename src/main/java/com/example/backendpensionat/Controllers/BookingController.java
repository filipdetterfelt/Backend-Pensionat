package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingSearchDTO;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;

    @GetMapping("bookings")
    public String allBookings(Model model) {
        model.addAttribute("bookingsList", bookingService.listAllBookings());
        return "bookings";
    }

    @GetMapping("/bookings/{id}/delete")
    public String deleteBookingById(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/bookings";
    }

    @GetMapping("/bookings/addBookings")
    public String addBookings(Model model) {
        model.addAttribute("customersList", customerService.listAllCustomers());
        model.addAttribute("roomsList", roomService.listAllRooms());
        return "addBookingsForm";
    }

    @GetMapping("/bookings/add/{id}/{startDate}/{endDate}/{roomType}")
    public String searchRoom(
            @PathVariable Long id,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate,
            @PathVariable RoomType roomType) {

        BookingSearchDTO search = new BookingSearchDTO(id, startDate, endDate, roomType);
        return "hej";
    }
}
