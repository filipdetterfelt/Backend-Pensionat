package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomerDetailedDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping("/ContractCustomers")
    public String contractCustomers(Model model){
        model.addAttribute("contractCustomersList",contractCustomerService.listAllContractCustomers());
        return "contractCustomers";
    }

    @GetMapping("/fullInformation")
    public String fullInfo(Model model){
        model.addAttribute("fullInfoCCustomer", contractCustomerService)
    }

    /*@PostMapping("deleteContractCustomer")
    public String removeContractCustomer(@ModelAttribute("deleteContractCustomer")ContractCustomerDetailedDTO cCustomer){
        cCustomer.setBookings(new ArrayList<ContractCustomerDTO>());
        contractCustomerService.removeContractCustomer(cCustomer);
        return "redirect:/ContractCustomers";
    }*/
}
