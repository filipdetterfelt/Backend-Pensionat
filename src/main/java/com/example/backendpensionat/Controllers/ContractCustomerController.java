package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.ContractCustomerDTO;
import com.example.backendpensionat.DTO.ContractCustomersSearchDTO;
import com.example.backendpensionat.Models.ContractCustomer;
import com.example.backendpensionat.Services.ContractCustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping("/ContractCustomers")
    public String contractCustomers(Model model){
        if(!model.containsAttribute("contractCustomersList")) {
            model.addAttribute("sortString", "contactName - ASC");
            model.addAttribute("contractCustomersList", contractCustomerService.listAllContractCustomers());
        }
        return "contractCustomers";
    }

    @GetMapping("/ContractCustomers/search")
    public String searchCustomer(
            @ModelAttribute ContractCustomersSearchDTO contractCustomersSearchDTO,
            RedirectAttributes rda,
            HttpSession session) {

        String column = contractCustomersSearchDTO.orderString.split(" - ")[0];
        String order = contractCustomersSearchDTO.orderString.split(" - ")[1];

        List<ContractCustomerDTO> filteredList = contractCustomerService.listSortedContractCustomers(contractCustomersSearchDTO.searchWord, column, order);

        rda.addFlashAttribute("contractCustomersList", filteredList);
        rda.addFlashAttribute("sortString", contractCustomersSearchDTO.orderString);
        session.setAttribute("searchWord", contractCustomersSearchDTO.searchWord);
        return "redirect:/ContractCustomers";
    }

    @GetMapping("ContractCustomers/sort/{sortString}")
    public String sortCustomers(
            @PathVariable String sortString,
            RedirectAttributes rda,
            HttpSession session) {

        String column = sortString.split(" - ")[0];
        String order = sortString.split(" - ")[1];
        String searchWord = session.getAttribute("searchWord") == null ? "" : (String) session.getAttribute("searchWord");

        List<ContractCustomerDTO> sortedCustomers = contractCustomerService.listSortedContractCustomers(searchWord, column, order);

        rda.addFlashAttribute("contractCustomersList", sortedCustomers);
        rda.addFlashAttribute("sortString", sortString);
        return "redirect:/ContractCustomers";
    }

    @GetMapping("/ContractCustomers/reset")
    public String resetSearch(HttpSession session) {
        session.removeAttribute("searchWord");
        return "redirect:/ContractCustomers";
    }

    @GetMapping("/fullInformation/{id}")
    public String fullInfo(@PathVariable Long id, Model model){
        ContractCustomer customer = contractCustomerService.findcCustomerById(id);
        model.addAttribute("fullInfoCCustomer", customer);
        return "fullInformation";
    }

    /*@PostMapping("deleteContractCustomer")
    public String removeContractCustomer(@ModelAttribute("deleteContractCustomer")ContractCustomerDetailedDTO cCustomer){
        cCustomer.setBookings(new ArrayList<ContractCustomerDTO>());
        contractCustomerService.removeContractCustomer(cCustomer);
        return "redirect:/ContractCustomers";
    }*/
}
