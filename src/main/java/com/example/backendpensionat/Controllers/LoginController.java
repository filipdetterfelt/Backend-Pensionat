package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Security.EmailTestSender;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.Impl.UserServiceIMPL;
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
    private final EmailTestSender emailTestSender;

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
        emailServiceIMPL.sendEmail("filip.exempel@mail.se","Reset your password", "Click on this link to reset");
        return "forgotPassword";
    }


    @PostMapping("/changePassword")
    public String changePw(@RequestParam("password") String password) {
        return "redirect:/changePassword";
    }
}
