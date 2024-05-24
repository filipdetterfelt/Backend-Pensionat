package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.UserEditDTO;
import com.example.backendpensionat.Models.User;
import com.example.backendpensionat.Services.UserService;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("usersList",userService.listAllUsers());
        model.addAttribute("deleteUser", new User());
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String userDetails(@PathVariable UUID id, RedirectAttributes rda , Model model){
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("allRoles", userService.listAllRoles());
            return "editUsers";
        }
        else {
            rda.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/users";
        }
    }

    @PostMapping("/users/edit/save")
    public String updateUsers(@ModelAttribute("user") UserEditDTO user){
        userService.changeUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/add")
    public String addUser(Model model){
        model.addAttribute("user", new UserEditDTO());
        model.addAttribute("allRoles", userService.listAllRoles());
        return "addUser";
    }

    @PostMapping("/users/add/save")
    public String saveUser(@ModelAttribute("user") UserEditDTO user){
        userService.addUser(user);
        return "redirect:/users";
    }

}
