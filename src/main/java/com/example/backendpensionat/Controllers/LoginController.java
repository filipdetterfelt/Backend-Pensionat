package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class LoginController {
    private final EmailServiceIMPL emailServiceIMPL;
    private UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPw() {
        return "forgotPassword";
    }

//    @RequestMapping("/sendForgotPwEmail")
//    public String sendForgotPwEmail(@RequestParam("username") String username, RedirectAttributes rda, Model model) {
//        User user = userService.findUserByUsername(username);
//        model.addAttribute("username", username);
//        String resetURL = "http://localhost:8080/resetPassword?token=123456";
//        emailServiceIMPL.sendEmail(user.getUsername(), "Reset your password", "Click on this link to reset" + resetURL);
//        return "redirect:/forgotPassword";
//    }

    @GetMapping("/changePassword")
    public String changePw() {
        return "changePassword";
    }
}
