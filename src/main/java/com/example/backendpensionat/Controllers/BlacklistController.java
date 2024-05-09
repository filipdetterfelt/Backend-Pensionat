package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.Services.Impl.BlacklistServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
}