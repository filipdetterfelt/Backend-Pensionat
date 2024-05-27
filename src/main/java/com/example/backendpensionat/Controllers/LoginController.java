package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.UserDTO;
import com.example.backendpensionat.Models.PasswordResetToken;
import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Repos.TokenRepo;
import com.example.backendpensionat.Repos.UserRepo;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.Impl.TokenService;
import com.example.backendpensionat.Services.Impl.UserServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@EnableAsync
public class LoginController {
    private final EmailServiceIMPL emailServiceIMPL;
    private final UserRepo userRepo;
    private UserServiceIMPL userService;
    private final TokenRepo tokenRepo;
    private TokenService tokenService;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPw(Model model) {

        model.addAttribute("user", new UserDTO());
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String sendForgotPwEmail(@RequestParam("email") String email) {
        String output = "";
        User user = userService.findUserByUsername(email);
        if (user != null) {
           output = emailServiceIMPL.sendForgotPasswordEmail(user);
        }

        if(output.equals("success")) {
            return "redirect:/forgotPassword?success";
        } else {
            return "redirect:/forgotPassword?error";
        }
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPassword(@PathVariable String token, Model model) {
        PasswordResetToken resetToken = tokenRepo.findByToken(token);
        if(resetToken != null && !emailServiceIMPL.hasExpired(resetToken.getExpiryDate())) {
            model.addAttribute("email", resetToken.getUser().getUsername());
            model.addAttribute("token", token);
            return "resetPassword";
        }
        return "redirect:/forgotPassword?expired";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordSave(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("token") String token) {
        User user = userService.findUserByUsername(email);
        if(user != null) {
            user.setPassword(encoder.encode(password));
            userRepo.save(user);
            tokenService.deleteByToken(token);
        }
        return "redirect:/login";
    }
}
