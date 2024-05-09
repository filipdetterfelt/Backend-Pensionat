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

    @GetMapping("/blacklistcustomers")
    public String showBlacklistForm(Model model) {
        model.addAttribute("blacklistcustomers", new BlacklistDetailedDTO());
        return "blacklistcustomers";
    }

    @GetMapping("/updateBlacklistStatus/{email}")
    public String updateBlacklistStatus(@PathVariable String email) {
        blacklistService.checkBlackListAndSetOkToFalse(email);
        return "redirect:/blacklist";
    }

    @PostMapping("/addBlacklistCustomer")
    @ResponseBody
    public String addCustomerToBlacklist(@RequestParam BlacklistDetailedDTO blacklistDetailedDTO) {
        blacklistService.addToBlacklist(blacklistDetailedDTO);
        return "Customer successfully added to blacklist!";
    }

    @PutMapping("/updateBlacklistStatus/{email}")
    @ResponseBody
    public String updateBlacklistStatus(@PathVariable String email, @RequestParam boolean isOk) {
        BlacklistDetailedDTO blacklistDTO = new BlacklistDetailedDTO();
        blacklistDTO.setEmail(email);
        blacklistDTO.setOk(isOk);

        blacklistService.updateBlacklistStatus(blacklistDTO);
        return "Blacklist status successfully updated!";
    }
}