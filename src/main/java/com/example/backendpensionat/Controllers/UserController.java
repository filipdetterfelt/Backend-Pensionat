package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Services.Impl.UserDetailsServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceIMPL userDetailsService;

    @GetMapping("/Users")
    public String users(Model model){
        model.addAttribute("usersList",userDetailsService.listAllUsers());
        model.addAttribute("deleteUser", new User());
        return "users";
    }

}
