package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
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
            model.addAttribute("bookings", new BookingDetailedDTO());
        }
        model.addAttribute("customersList", customerService.listAllCustomers());

        List<RoomType> roomTypeList = List.of(RoomType.SINGLE, RoomType.DOUBLE, RoomType.SUITE);
        model.addAttribute("roomTypeList", roomTypeList);

        return "addBookingsForm";
    }

    @GetMapping("/bookings/add/{id}/{startDate}/{endDate}/{roomType}")
    public String searchRoom(
            @PathVariable Long id,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate,
            @PathVariable String roomType,
            RedirectAttributes rda) {

        CustomerDetailedDTO customer = customerService.findCustomerById(id);
        BookingSearchDTO bookingSearch = new BookingSearchDTO(customer, startDate, endDate, RoomType.getRoomTypeByString(roomType));
        List<RoomDetailedDTO> roomList = roomService.listFreeRoomsByRoomType(bookingSearch);

        rda.addFlashAttribute("bookingSearch", bookingSearch);
        rda.addFlashAttribute("roomsList", roomList);

        System.out.println(roomType);

        return "redirect:/bookings/addBookings";
    }

    @GetMapping("/bookings/add/{customerId}/{startDate}/{endDate}/{extraBeds}/{roomNo}")
    public String addBooking(
            @PathVariable Long customerId,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate,
            @PathVariable int extraBeds,
            @PathVariable Long roomNo) {
        CustomerDTO customer = CustomerDTO.builder().id(customerId).build();
        RoomDetailedDTO room = roomService.findRoomById(roomNo);

        BookingDetailedDTO booking = BookingDetailedDTO.builder()
                .amountOfBeds(extraBeds)
                .startDate(startDate)
                .endDate(endDate)
                .customerDTO(customer)
                .room(room)
                .build();

        bookingService.saveBooking(booking);

        return "redirect:/bookings";
    }

    @GetMapping("/bookings/{id}/edit")
    public String editBookingById(@PathVariable Long id, Model model) {
        BookingDetailedDTO bookingDTO = bookingService.findBookingById(id);
        RoomSearchDTO roomSearch = new RoomSearchDTO(bookingDTO.getStartDate(), bookingDTO.getEndDate(), 0);
        List<RoomDetailedDTO> listFreeRooms = roomService.listFreeRooms(roomSearch);

        if(!model.containsAttribute("refreshed")) {
            model.addAttribute("listFreeRooms", listFreeRooms);
            model.addAttribute("booking", bookingDTO);
        }

        return "editBookingsForm";
    }

    @GetMapping("/bookings/edit/refresh/{startDate}/{endDate}/{bookingId}")
        public String editRefresh(
                @PathVariable LocalDate startDate,
                @PathVariable LocalDate endDate,
                @PathVariable Long bookingId,
                RedirectAttributes rda) {

            RoomSearchDTO roomSearch = new RoomSearchDTO(startDate, endDate, 0);
            BookingDetailedDTO booking = bookingService.findBookingById(bookingId);
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            List<RoomDetailedDTO> listFreeRooms = roomService.listFreeRooms(roomSearch);
            rda.addFlashAttribute("listFreeRooms", listFreeRooms);
            rda.addFlashAttribute("refreshed", true);
            rda.addFlashAttribute("booking", booking);

            return "redirect:/bookings/" + bookingId + "/edit";
        }

    @PostMapping("/bookings/update")
    public String updateBookingPost(@ModelAttribute("booking") BookingDetailedDTO bookingDTO) {
        String roomNo = bookingDTO.getRoomNumber().split(" - ")[0];
        RoomDetailedDTO room = roomService.findRoomNumber(Long.parseLong(roomNo));
        CustomerDTO customer = customerService.findCustomerByBookingID(bookingDTO.getId());

        bookingDTO.setCustomerDTO(customer);
        bookingDTO.setRoom(room);

        bookingService.updateBooking(bookingDTO);
        return "redirect:/bookings";
    }




}
