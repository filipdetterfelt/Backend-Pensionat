package com.example.backendpensionat.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("customers")
    public String customers() {
        return "customers";
    }
}
