package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDetailedDTO;
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
        emailServiceIMPL.sendEmail("michael.s_97@hotmail.com", "Test", "Test");
        return "emailConfirmation";
    }
}
