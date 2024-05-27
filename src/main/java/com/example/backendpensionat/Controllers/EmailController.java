package com.example.backendpensionat.Controllers;


import com.example.backendpensionat.DTO.BookingDetailedDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.DTO.EmailTemplateDTO;
import com.example.backendpensionat.Models.EmailTemplate;
import com.example.backendpensionat.Repos.EmailTemplateRepo;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.Impl.EmailTemplateServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class EmailController {
    private final EmailServiceIMPL emailServiceIMPL;
    private final EmailTemplateServiceIMPL emailTemplateServiceIMPL;

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail(Model model) {
        BookingDetailedDTO booking = (BookingDetailedDTO) model.getAttribute("booking");
        CustomerDetailedDTO customer = (CustomerDetailedDTO) model.getAttribute("customer");
        EmailTemplateDTO template = emailTemplateServiceIMPL.getTemplate();

        String message = emailTemplateServiceIMPL.emailMessage(template, customer, booking);
        emailServiceIMPL.sendEmail(customer.getEmail(), template.getSubject(), message);

        return "emailConfirmation";
    }
}
