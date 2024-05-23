package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Controller
public class EmailController {

    private final EmailServiceIMPL emailServiceIMPL;

    @RequestMapping("/sendConfirmationEmail")
    public String sendConfirmationEmail() {
        emailServiceIMPL.sendEmail("michael.s_97@hotmail.com", "Test", "Test");
        return "emailConfirmation";
    }
}
