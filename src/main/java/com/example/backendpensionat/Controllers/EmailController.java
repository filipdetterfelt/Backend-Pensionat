package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.Security.EmailProperties;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class EmailController {

    private final EmailServiceIMPL emailServiceIMPL;
    private final EmailProperties emailProperties;

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail(Model model) {
        BookingDetailedDTO booking = (BookingDetailedDTO) model.getAttribute("booking");
        emailServiceIMPL.sendEmail(emailProperties.getConfirmationRecipient(), "Test", "Test");
        return "emailConfirmation";
    }
}
