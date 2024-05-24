package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Security.EmailProperties;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class LoginController {
    private final EmailServiceIMPL emailServiceIMPL;
    private final EmailProperties emailProperties;

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
        emailServiceIMPL.sendEmail(emailProperties.getForgotPasswordRecipient(),"Reset your password", "Click on this link to reset");
        return "forgotPassword";
    }

    @PostMapping("/changePassword")
    public String changePw(@RequestParam("password") String password) {
        return "redirect:/changePassword";
    }
}
