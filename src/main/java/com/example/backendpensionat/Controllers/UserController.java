package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Services.Impl.UserDetailsServiceIMPL;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/Users/{id}")
    public String userDetails(@PathVariable UUID id, RedirectAttributes rda , HttpSession session){
        Optional<User> user = userDetailsService.findUserById(id);
        if(user.isPresent()) {


            session.setAttribute("updatedUser", user);
            rda.addFlashAttribute("updatedUser", user);
            return "redirect:/editUsers";
        }
        else{
            rda.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/Users";
        }
    }

    @PostMapping("/updateUsers")
    public String updateUsers(@ModelAttribute("updatedUser") User user, HttpSession session){
        User u = (User) session.getAttribute("updatedUser");
        user.setUsername(u.getUsername());
        user.setPassword(u.getPassword());
        user.setRoles(u.getRoles());
        userDetailsService.changeUser(user);
        return "editUsers";
    }

}
