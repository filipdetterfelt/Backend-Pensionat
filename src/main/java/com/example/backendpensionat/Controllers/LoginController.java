package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Services.Impl.EmailServiceIMPL;
import com.example.backendpensionat.Services.Impl.UserServiceIMPL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@AllArgsConstructor
@Controller
public class LoginController {
    private final EmailServiceIMPL emailServiceIMPL;
    private final UserServiceIMPL userServiceIMPL;


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
        return "/changePassword";
    }




    //TEST
    @PostMapping("/sendForgotPwEmail")
    public String sendForgotPwEmail(@RequestParam("email") String email) {
        String token = UUID.randomUUID().toString();
        userServiceIMPL.createPasswordResetTokenForUser(email,token);

        String resetUrl = "http://localhost:8080/changePassword?token=" + token;
        return "redirect:/forgotPassword?emailSent";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordPage(@RequestParam("token") String token, Model model) {
        String result = userServiceIMPL.validatePasswordResetToken(token);

        if(result != null){
            return "redirect:/login?error";
        }
        model.addAttribute("token", token);
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("token") String token,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPassword") String confirmPassword) {
        if(!password.equals(confirmPassword)){
            return "redirect:/changePassword?token" + token + "&error=passwordsDoNotMatch";
        }
        String result = userServiceIMPL.validatePasswordResetToken(token);
        if(result != null){
            return "redirect:/login?error";
        }

        User user = userServiceIMPL.getUserByPasswordResetToken(token);
        user.updatePassword(user,password);
        return "redirect:/login?message=passwordChanged";


    }
}
