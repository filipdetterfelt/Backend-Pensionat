package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.Services.Impl.BlacklistServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class BlacklistController {

    private final BlacklistServiceIMPL blacklistService;

    @GetMapping("/blacklistCustomers")
    public String showBlacklistForm(Model model) {
        model.addAttribute("blacklistCustomers", new BlacklistDetailedDTO());
        return "blacklistCustomers";
    }

    @PostMapping("/blacklist/add")
    public String addCustomerToBlacklist(@RequestParam(name = "email") String email, @RequestParam(name = "name")  String name) {
        blacklistService.addCustomerToBlacklist(email, name);
        return "redirect:/blacklistCustomers";
    }

    @GetMapping("/blacklist/updateTrue")
    public String updateBlacklistStatusTrue(@RequestParam(name = "email") String email) {
        blacklistService.updateCustomerInBlacklistToTrue(email);
        return "redirect:/blacklistCustomers";
    }

    @GetMapping("/blacklist/updateFalse")
    public String updateBlacklistStatusFalse(@RequestParam(name = "email") String email) {
        blacklistService.updateCustomerInBlacklistToFalse(email);
        return "redirect:/blacklistCustomers";
    }
}