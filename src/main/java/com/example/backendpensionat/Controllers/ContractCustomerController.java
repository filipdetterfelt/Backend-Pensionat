package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomersSearchDTO;
import com.example.backendpensionat.Services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping("/ContractCustomers")
    public String contractCustomers(Model model){
        if(!model.containsAttribute("contractCustomersList")) {
            model.addAttribute("contractCustomersList", contractCustomerService.listAllContractCustomers());
        }
        return "contractCustomers";
    }

    @GetMapping("/ContractCustomers/search")
    public String searchCustomer(
            @ModelAttribute ContractCustomersSearchDTO contractCustomersSearchDTO,
            RedirectAttributes rda) {

        String column = contractCustomersSearchDTO.orderString.split(" - ")[0];
        String order = contractCustomersSearchDTO.orderString.split(" - ")[1];

        List<ContractCustomerDTO> list = contractCustomerService.listSortedContractCustomers(column, order);

        List<ContractCustomerDTO> filteredList = list.stream().filter(customer ->
                customer.getCompanyName().contains(contractCustomersSearchDTO.searchWord) ||
                customer.getContactName().contains(contractCustomersSearchDTO.searchWord) ||
                customer.getCountry().contains(contractCustomersSearchDTO.searchWord)).toList();

        rda.addFlashAttribute("contractCustomersList", filteredList);
        rda.addFlashAttribute("sortString", contractCustomersSearchDTO.orderString);
        return "redirect:/ContractCustomers";
    }

    @GetMapping("ContractCustomers/sort/{sortString}")
    public String sortCustomers(@PathVariable String sortString,
                                RedirectAttributes rda) {
        String column = sortString.split(" - ")[0];
        String order = sortString.split(" - ")[1];

        List<ContractCustomerDTO> sortedCustomers = contractCustomerService.listSortedContractCustomers(column, order);

        rda.addFlashAttribute("contractCustomersList", sortedCustomers);
        rda.addFlashAttribute("sortString", sortString);
        return "redirect:/ContractCustomers";
    }

    /*@PostMapping("deleteContractCustomer")
    public String removeContractCustomer(@ModelAttribute("deleteContractCustomer")ContractCustomerDetailedDTO cCustomer){
        cCustomer.setBookings(new ArrayList<ContractCustomerDTO>());
        contractCustomerService.removeContractCustomer(cCustomer);
        return "redirect:/ContractCustomers";
    }*/
}
