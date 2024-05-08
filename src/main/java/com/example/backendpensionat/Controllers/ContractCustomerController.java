package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping("/ContractCustomers")
    public String contractCustomers(Model model){
        model.addAttribute("contractCustomersList",contractCustomerService.listAllContractCustomers());
        return "contractCustomers";
    }
}
