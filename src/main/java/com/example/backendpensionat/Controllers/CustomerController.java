package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BlacklistDetailedDTO;
import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Services.BlacklistService;
import com.example.backendpensionat.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final BlacklistService blacklistService;


    @GetMapping("customers")
    public String customers(Model model) {
        model.addAttribute("customersList", customerService.listAllCustomers());
        model.addAttribute("deleteCustomer", new CustomerDetailedDTO());
        return "customers";
    }

    @GetMapping("addNewCustomer")
    public String addNewCustomer(Model model) {
        model.addAttribute("newCustomers", new CustomerDetailedDTO());
        return "addNewCustomerForm";
    }

    @GetMapping("/customers/blacklisted")
    public String isBlackListed() {
        return "blacklisted";
    }

    @PostMapping("addCustomer")
    public String addCustomers(@ModelAttribute("newCustomers") CustomerDetailedDTO customer){
        BlacklistDetailedDTO blacklist = blacklistService.checkBlackList(customer.getEmail());
        if(blacklist.isOk()) {
            customer.setBookings(new ArrayList<BookingDTO>());
            customerService.addCustomer(customer);
            return "redirect:/customers";
        } else {
            return "redirect:/customers/blacklisted";
        }

    }

    @PostMapping("UpdateCustomers")
    public String updateCustomer(@ModelAttribute ("updatedCustomer") CustomerDetailedDTO customer){
        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.changeCustomer(customer);
        return "redirect:/customers";
    }

    @PostMapping("deleteCustomer")
    public String removeCustomer(@ModelAttribute("deleteCustomer") CustomerDetailedDTO customer) {
        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.removeCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("UpdateCustomer")
    public String updateCustomer(Model model) {
        System.out.println(model.containsAttribute("updatedCustomer"));
        return "updateCustomerForm";
    }

    @GetMapping("customers/{id}")
    public String editCustomerById(@PathVariable Long id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("updatedCustomer",customerService.findCustomerById(id));
        return "redirect:/UpdateCustomer";
    }

    @GetMapping("newCurrentCustomer")
    public String newCurrentCustomer() {
        return "newCurrentCustomer";
    }
}
