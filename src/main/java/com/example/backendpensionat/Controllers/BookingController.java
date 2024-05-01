package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.BookingSearchDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
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
        if (!model.containsAttribute("bookingSearch")) {
            model.addAttribute("roomsList", roomService.listAllRooms());
            model.addAttribute("customersList", customerService.listAllCustomers());
        }

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

    @GetMapping("/bookings/add/{id}/{startDate}/{endDate}/{roomType}")
    public String searchRoom(
            @PathVariable Long id,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate,
            @PathVariable int roomType,
            RedirectAttributes rda) {

        CustomerDetailedDTO customer = customerService.findCustomerById(id);
        BookingSearchDTO bookingSearch = new BookingSearchDTO(customer, startDate, endDate, RoomType.getRoomType(roomType - 1));
        RoomSearchDTO roomSearch = new RoomSearchDTO(startDate, endDate, RoomType.getRoomType(roomType - 1).getExtraBeds());
        List<RoomDetailedDTO> roomList = roomService.listFreeRooms(roomSearch);

        rda.addFlashAttribute("bookingSearch", bookingSearch);
        rda.addFlashAttribute("roomsList", roomList);

        return "redirect:/bookings/addBookings";
    }
}
