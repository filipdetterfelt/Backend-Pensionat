package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/bookings/{id}/edit")
    public String editBookingById(@PathVariable Long id, Model model) {
        BookingDetailedDTO bookingDTO = bookingService.findBookingById(id);
        model.addAttribute("booking", bookingDTO);
        return "editBookingsForm";
    }

    @PostMapping("/bookings/update")
    public String updateBooking(@ModelAttribute("booking") BookingDetailedDTO bookingDTO) {
        bookingService.updateBooking(bookingDTO);
        return "redirect:/bookings";
    }
}
