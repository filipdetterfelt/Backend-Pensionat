package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingFilterDTO;
import com.example.backendpensionat.DTO.RoomDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Models.Booking;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if(!model.containsAttribute("setDates")) {
            System.out.println("setDates does not exist");
            model.addAttribute("roomsList", roomService.listAllRoomsExpanded());
        } else {
            BookingFilterDTO bfdto = (BookingFilterDTO) model.getAttribute("setDates");
            System.out.println(bfdto.getStartDate());
            System.out.println(bfdto.getEndDate());
            System.out.println(bfdto.getCustomerName());
        }

        model.addAttribute("customersList", customerService.listAllCustomers());
        return "addBookingsForm";
    }

    @GetMapping("/bookings/add/{startDate}/{endDate}/{customerId}")
    public String getFilteredRooms(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Long customerId, RedirectAttributes rda) {
        String customerName = customerService.getCustomerNameById(customerId);

        RoomSearchDTO roomSearchDTO = new RoomSearchDTO(startDate, endDate, 1);
        BookingFilterDTO setDates = new BookingFilterDTO(startDate.toString(), endDate.toString(), customerName);

        List<RoomDetailedDTO> roomsList =  roomService.listFreeRooms(roomSearchDTO);
        rda.addFlashAttribute("roomsList", roomsList);
        rda.addFlashAttribute("setDates", setDates);
        return "redirect:/bookings/addBookings";
    }
}
