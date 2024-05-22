package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Services.Impl.UserDetailsServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    public UserDetailsServiceIMPL userDetailsService;

    @GetMapping("/Users")
    public String Users(){
        return "users";
    }

}
