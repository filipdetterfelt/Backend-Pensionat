package com.example.backendpensionat.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookingController {

    @GetMapping("bookings")
    public String allBookings() {
        return "bookings";
    }
}
