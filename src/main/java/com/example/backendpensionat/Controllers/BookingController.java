package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Services.BlacklistService;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final CustomerService customerService;
    private final RoomService roomService;
    private final BlacklistService blacklistService;

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

    @GetMapping("/bookings/add")
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

    @PostMapping("/bookings/add")
    public String addBookingFromNewCustomer(@ModelAttribute("newCustomers") CustomerDetailedDTO customer,
                                            Model model,
                                            RedirectAttributes rda) {
        BlacklistDetailedDTO blacklist = blacklistService.checkBlackList(customer.getEmail());
        if(!blacklist.isOk()) {
            rda.addFlashAttribute("wasBlackListed", true);
            return "redirect:/addNewCustomer";
        }

        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.addCustomer(customer);

        if (!model.containsAttribute("bookingSearch")) {
            model.addAttribute("roomsList", roomService.listAllRooms());
            model.addAttribute("bookings", new BookingDetailedDTO());
        }
        model.addAttribute("customersList", customerService.listAllCustomers());

        List<RoomType> roomTypeList = List.of(RoomType.SINGLE, RoomType.DOUBLE, RoomType.SUITE);
        model.addAttribute("roomTypeList", roomTypeList);

        return "addBookingsForm";
    }



    @PostMapping("/bookings/add/refresh")
    public String refreshRoomBox(
            @RequestParam("customerInfo") String id,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("roomType") RoomType roomType,
            RedirectAttributes rda) {

        CustomerDetailedDTO customer = customerService.findCustomerById(Long.parseLong(id.split(": ")[0]));
        BookingSearchDTO bookingSearch = new BookingSearchDTO(customer, startDate, endDate, roomType);
        List<RoomDetailedDTO> roomList = roomService.listFreeRoomsByRoomType(bookingSearch);

        rda.addFlashAttribute("bookingSearch", bookingSearch);
        rda.addFlashAttribute("roomsList", roomList);

        System.out.println(roomType);

        return "redirect:/bookings/add";
    }

//    @PostMapping("/bookings/add/save")
//    public String saveBooking(
//            @RequestParam("customerInfo") String id,
//            @RequestParam("startDate") LocalDate startDate,
//            @RequestParam("endDate") LocalDate endDate,
//            @RequestParam("amountOfBeds") int extraBeds,
//            @RequestParam("roomNumber") Long roomNo) {
//        Long customerId = Long.parseLong(id.split(": ")[0]);
//
//        CustomerDTO customer = CustomerDTO.builder().id(customerId).build();
//        RoomDetailedDTO room = roomService.findRoomById(roomNo);
//
//        BookingDetailedDTO booking = BookingDetailedDTO.builder()
//                .amountOfBeds(extraBeds)
//                .startDate(startDate)
//                .endDate(endDate)
//                .customerDTO(customer)
//                .room(room)
//                .build();
//
//        bookingService.saveBooking(booking);
//
//        return "redirect:/bookings";
//    }

    @PostMapping("/bookings/add/save")
    public String saveBooking(
            @RequestParam("customerInfo") String id,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("amountOfBeds") int extraBeds,
            @RequestParam("roomNumber") Long roomNo) {
        Long customerId = Long.parseLong(id.split(": ")[0]);

        CustomerDTO customer = CustomerDTO.builder().id(customerId).build();
        RoomDetailedDTO room = roomService.findRoomById(roomNo);

        Double totalPrice = bookingService.calculateTotalPrice(startDate, endDate, room.getPrice());
        System.out.println("1: " + totalPrice);

        BookingDetailedDTO booking = BookingDetailedDTO.builder()
                .amountOfBeds(extraBeds)
                .startDate(startDate)
                .endDate(endDate)
                .totalPrice(totalPrice)
                .customerDTO(customer)
                .room(room)
                .build();

        System.out.println("2: " + totalPrice);

        bookingService.saveBooking(booking);

        System.out.println("3: " + totalPrice);

        return "redirect:/bookings";
    }

    @GetMapping("/bookings/edit/{id}")
    public String editBookingById(@PathVariable Long id, Model model) {
        BookingDetailedDTO bookingDTO = bookingService.findBookingById(id);

        String placeholder = bookingDTO.getRoom().getRoomNumber() + " - " + bookingDTO.getRoom().getRoomType();
        bookingDTO.setRoomNumber(placeholder);
        System.out.println(bookingDTO.getRoomNumber());

        RoomSearchDTO roomSearch = new RoomSearchDTO(bookingDTO.getStartDate(), bookingDTO.getEndDate(), 0);
        List<RoomDetailedDTO> listFreeRooms = roomService.listFreeRooms(roomSearch);

        if(!model.containsAttribute("refreshed")) {
            model.addAttribute("listFreeRooms", listFreeRooms);
            model.addAttribute("booking", bookingDTO);
        }
        return "editBookingsForm";
    }

    @PostMapping("/bookings/edit/refresh")
        public String editRefresh(@ModelAttribute BookingDetailedDTO booking, RedirectAttributes rda) {
            RoomSearchDTO roomSearch = new RoomSearchDTO(booking.getStartDate(), booking.getEndDate(), 0);
            List<RoomDetailedDTO> listFreeRooms = roomService.listFreeRooms(roomSearch);

            rda.addFlashAttribute("listFreeRooms", listFreeRooms);
            rda.addFlashAttribute("refreshed", true);
            rda.addFlashAttribute("booking", booking);

            return "redirect:/bookings/edit/" + booking.getId();
        }

    @PostMapping("/bookings/edit/save")
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
