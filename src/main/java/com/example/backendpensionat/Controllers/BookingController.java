package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.*;
import com.example.backendpensionat.Enums.RoomType;
import com.example.backendpensionat.Services.BlacklistService;
import com.example.backendpensionat.Services.BookingService;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import jakarta.servlet.http.HttpSession;
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
        if (!blacklist.isOk()) {
            rda.addFlashAttribute("wasBlackListed", true);
            return "redirect:/addNewCustomer";
        }

        customer.setBookings(new ArrayList<>());

        if (customerService.doesCustomerExist(customer.getSsn())) {
            customerService.changeCustomer(customer);
            System.out.println("kund finns redan");
        } else {
            customerService.addCustomer(customer);
            System.out.println("ny kund skapad");
        }

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
            @RequestParam(value = "roomNumber", required = false, defaultValue = "0") Long roomNumber,
            @RequestParam(value = "amountOfBeds", required = false, defaultValue = "0") int amountOfBeds,
            @RequestParam(value = "totalPrice", required = false, defaultValue = "0.0") Double totalPrice,
            RedirectAttributes rda) {

        CustomerDetailedDTO customer = customerService.findCustomerById(Long.parseLong(id.split(": ")[0]));
        BookingSearchDTO bookingSearch = new BookingSearchDTO(customer, startDate, endDate, roomType, roomNumber, amountOfBeds, totalPrice);
        List<RoomDetailedDTO> roomList = roomService.listFreeRoomsByRoomType(bookingSearch);

        bookingSearch.setTotalPrice(bookingService.calculateTotalPrice(bookingSearch.getStartDate(), bookingSearch.getEndDate(), bookingSearch.getRoomType().getRoomTypePrice(), customer));

        System.out.println(totalPrice);
        System.out.println(bookingSearch.getTotalPrice());

        rda.addFlashAttribute("bookingSearch", bookingSearch);
        rda.addFlashAttribute("roomsList", roomList);

        System.out.println(roomType);

        return "redirect:/bookings/add";
    }

    @PostMapping("/bookings/add/save")
    public String saveBooking(
            @RequestParam("customerInfo") String id,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("amountOfBeds") int extraBeds,
            @RequestParam("roomNumber") Long roomNo,
            @RequestParam("totalPrice") Double totalPrice,
            RedirectAttributes rda) {

        Long customerId = Long.parseLong(id.split(": ")[0]);

        CustomerDTO customer = CustomerDTO.builder().id(customerId).build();
        RoomDetailedDTO room = roomService.findRoomNumber(roomNo);

        BookingDetailedDTO booking = BookingDetailedDTO.builder()
                .amountOfBeds(extraBeds)
                .startDate(startDate)
                .endDate(endDate)
                .totalPrice(totalPrice)
                .customerDTO(customer)
                .room(room)
                .build();

        bookingService.saveBooking(booking);

        rda.addFlashAttribute("booking", booking);
        return "redirect:/sendConfirmationEmail";
    }

    @GetMapping("/bookings/edit/{id}")
    public String editBookingById(@PathVariable Long id, HttpSession session, Model model) {
        BookingDetailedDTO bookingDTO = bookingService.findBookingById(id);

        String placeholder = bookingDTO.getRoom().getRoomNumber() + " - " + bookingDTO.getRoom().getRoomType();
        bookingDTO.setRoomNumber(placeholder);

        RoomSearchDTO roomSearch = new RoomSearchDTO(bookingDTO.getStartDate(), bookingDTO.getEndDate(), 0);
        List<RoomDetailedDTO> listFreeRooms = roomService.listFreeRooms(roomSearch);

        if (!model.containsAttribute("refreshed")) {
            model.addAttribute("listFreeRooms", listFreeRooms);
            model.addAttribute("booking", bookingDTO);
            session.setAttribute("customer", bookingDTO.getCustomerDTO());
            session.setAttribute("room", bookingDTO.getRoom());
        }
        return "editBookingsForm";
    }

    @PostMapping("/bookings/edit/refresh")
    public String editRefresh(@ModelAttribute BookingDetailedDTO booking, RedirectAttributes rda, HttpSession session) {
        RoomSearchDTO roomSearch = new RoomSearchDTO(booking.getStartDate(), booking.getEndDate(), 0);
        List<RoomDetailedDTO> listFreeRooms = roomService.listFreeRooms(roomSearch);
        CustomerDetailedDTO customer = customerService.findCustomerById(((CustomerDTO) session.getAttribute("customer")).getId());

        booking.setCustomerDTO((CustomerDTO) session.getAttribute("customer"));
        booking.setRoom((RoomDetailedDTO) session.getAttribute("room"));
        Double totalPrice = bookingService.calculateTotalPrice(booking.getStartDate(), booking.getEndDate(), booking.getRoom().getRoomType().getRoomTypePrice(), customer);

        booking.setTotalPrice(totalPrice);

        rda.addFlashAttribute("listFreeRooms", listFreeRooms);
        rda.addFlashAttribute("refreshed", true);
        rda.addFlashAttribute("booking", booking);
        System.out.println(booking.getTotalPrice());
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
