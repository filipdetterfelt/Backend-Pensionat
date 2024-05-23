package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailServiceIMPL emailServiceIMPL;

    public EmailController(EmailServiceIMPL emailServiceIMPL) {
        this.emailServiceIMPL = emailServiceIMPL;
    }

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail() {
        emailServiceIMPL.sendEmail("michael.s_97@hotmail.com", "Test", "Test");
        return "Email sent";
    }
}
