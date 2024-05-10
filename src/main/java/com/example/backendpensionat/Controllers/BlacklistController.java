package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.Services.Impl.BlacklistServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class BlacklistController {

    private final BlacklistServiceIMPL blacklistService;

    @GetMapping("/blacklistCustomers")
    public String showBlacklistForm() {
        return "blacklistCustomers";
    }

    @PostMapping("/blacklist/add")
    public String addCustomerToBlacklist(@RequestParam(name = "email") String email, @RequestParam(name = "name")  String name, RedirectAttributes rda) {
        blacklistService.addCustomerToBlacklist(email, name);
        rda.addFlashAttribute("isAdded", true);
        return "redirect:/blacklistCustomers";
    }

    @GetMapping("/blacklist/updateTrue")
    public String updateBlacklistStatusTrue(@RequestParam(name = "email") String email, RedirectAttributes rda) {
        blacklistService.updateCustomerInBlacklistToTrue(email);
        rda.addFlashAttribute("isGreenlisted", true);
        return "redirect:/blacklistCustomers";
    }

    @GetMapping("/blacklist/updateFalse")
    public String updateBlacklistStatusFalse(@RequestParam(name = "email") String email, RedirectAttributes rda) {
        blacklistService.updateCustomerInBlacklistToFalse(email);
        rda.addFlashAttribute("isBlacklisted", true);
        return "redirect:/blacklistCustomers";
    }
}