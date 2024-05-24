package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class EmailController {

    private final EmailServiceIMPL emailServiceIMPL;

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail(Model model) {
        BookingDetailedDTO booking = (BookingDetailedDTO) model.getAttribute("booking");
        CustomerDetailedDTO customer = (CustomerDetailedDTO) model.getAttribute("customer");
        System.out.println(customer.getEmail());
        emailServiceIMPL.sendEmail(customer.getEmail(), "Booking confirmation Pensionat Koriander", "Dear " + customer.getFirstName() + " " + customer.getLastName() + ",\n\n" +
                "Thank you for booking a room at Pensionat Koriander. Your booking is confirmed.\n\n" +
                "Booking details:\n" +
                "Room number: " + booking.getRoom().getRoomNumber() + "\n" +
                "Room type: " + booking.getRoom().getRoomType() + "\n" +
                "Check-in date: " + booking.getStartDate() + "\n" +
                "Check-out date: " + booking.getEndDate() + "\n\n" +
                "Price: " + booking.getTotalPrice() + " SEK\n\n" +
                "We are looking forward to welcoming you at Pensionat Koriander.\n\n" +
                "Best regards,\n" +
                "Pensionat Koriander");
        return "emailConfirmation";
    }
}
