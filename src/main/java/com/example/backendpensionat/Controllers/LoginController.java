package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Security.EmailTestSender;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.Impl.UserServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        emailTestSender.sendTestEmail();
        return "forgotPassword";
    }


}
