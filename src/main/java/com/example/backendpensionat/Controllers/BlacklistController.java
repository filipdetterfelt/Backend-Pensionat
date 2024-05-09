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

//    @GetMapping("/updateBlacklistStatus/{email}")
//    public String updateBlacklistStatus(@PathVariable String email) {
//        blacklistService.checkBlackListAndSetOkToFalse(email);
//        return "redirect:/blacklist";
//    }

    @PostMapping("/blacklist/add")
    public String addCustomerToBlacklist(@RequestParam String email) {
        BlacklistDetailedDTO newBlacklistedCustomer = new BlacklistDetailedDTO();
        newBlacklistedCustomer.setEmail(email);
        newBlacklistedCustomer.setOk(false);
        blacklistService.addCustomerToBlacklist(email);
        return "redirect:/blacklistCustomers";
    }

    @PutMapping("/blacklist/update")
    @ResponseBody
    public String updateBlacklistStatus(@RequestParam String email) {
        blacklistService.updateCustomerInBlacklist(email);
        return "redirect:/blacklistCustomers";
    }
}