package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.Impl.UserServiceIMPL;
import com.example.backendpensionat.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@AllArgsConstructor
@Controller
@EnableAsync
public class LoginController {
    private final EmailServiceIMPL emailServiceIMPL;
    private UserServiceIMPL userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPw() {
        return "forgotPassword";
    }

    @RequestMapping("/sendForgotPwEmail")
    public String sendForgotPwEmail(@RequestParam("username") String username, RedirectAttributes rda, Model model) {
        Optional<User> user = Optional.ofNullable(userService.findUserByUsername(username));

        if (user.isPresent()) {
            model.addAttribute("username", username);
            String resetURL = "http://localhost:8080/changePassword";
            String emailContent = "Click on link below to reset your password: <br><a href=\"" + resetURL + "\">Reset Password</a>";
            emailServiceIMPL.sendEmailHTML(username, "Reset your password", emailContent);
            rda.addFlashAttribute("successfulMessage", true);
            return "redirect:/forgotPassword";
        } else {
            rda.addFlashAttribute("errorMessage", true);
            return "redirect:/forgotPassword";
        }
    }

    @GetMapping("/changePassword")
    public String changePw() {
        return "changePassword";
    }
}
