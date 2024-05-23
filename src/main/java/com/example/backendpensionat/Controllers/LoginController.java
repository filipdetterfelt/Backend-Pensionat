package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class LoginController {
    private final EmailServiceIMPL emailServiceIMPL;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPw() {
        return "forgotPassword";
    }

    @RequestMapping("/sendForgotPwEmail")
    public String sendForgotPwEmail() {
        emailServiceIMPL.sendEmail("filip.detterfelt@yh.nackademin.se", "Reset your Password", "Click on this link to reset your password");
        return "forgotPassword";
    }


}
