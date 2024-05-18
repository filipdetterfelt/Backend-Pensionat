package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Services.Impl.BlacklistServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class BlacklistController {

    private final BlacklistServiceIMPL blacklistService;

    @GetMapping("/blacklistCustomers")
    public String showBlacklistForm() {
        return "blacklistCustomers";
    }

    @PostMapping("/blacklist/add")
    public String addCustomerToBlacklist(@RequestParam(name = "email") String email, @RequestParam(name = "name")  String name, RedirectAttributes rda) throws IOException {
        rda.asMap().clear();
        boolean blacklisted = blacklistService.getBlacklist(false).stream().anyMatch(b -> b.email.equals(email));

        if (blacklisted) {
            rda.addFlashAttribute("blacklistExists", true);
            System.out.println("in if-blacklisted - isBlacklisted");
        } else {
            blacklistService.addCustomerToBlacklist(email, name);
            rda.addFlashAttribute("isAdded", true);
            System.out.println("in if-blacklisted - isAdded");
        }

        return "redirect:/blacklistCustomers";
    }

    @GetMapping("/blacklist/updateTrue")
    public String updateBlacklistStatusTrue(@RequestParam(name = "email") String email, RedirectAttributes rda) {
        rda.asMap().clear();
        blacklistService.updateCustomerInBlacklistToTrue(email);
        rda.addFlashAttribute("isGreenlisted", true);
        System.out.println("in updateTrue");
        return "redirect:/blacklistCustomers";
    }

    @GetMapping("/blacklist/updateFalse")
    public String updateBlacklistStatusFalse(@RequestParam(name = "email") String email, RedirectAttributes rda) {
        rda.asMap().clear();
        blacklistService.updateCustomerInBlacklistToFalse(email);
        rda.addFlashAttribute("isBlacklisted", true);
        System.out.println("in updateFalse");
        return "redirect:/blacklistCustomers";
    }
}