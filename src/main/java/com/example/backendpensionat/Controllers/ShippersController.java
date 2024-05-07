package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Services.ShippersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ShippersController {

    private final ShippersService service;

    @GetMapping("/shippers")
    public String allShippers(Model model){
     return "EJ KLAR!!!!";
    }


}
