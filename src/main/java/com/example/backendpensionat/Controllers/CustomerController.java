package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Services.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


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
    public String addCustomers(@ModelAttribute("newCustomers") CustomerDetailedDTO customer) {

        customer.setBookings(new ArrayList<>());
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }

    @PostMapping("UpdateCustomers")
    public String updateCustomer(@ModelAttribute("updatedCustomer") CustomerDetailedDTO customer, HttpSession session) {
        CustomerDetailedDTO c = (CustomerDetailedDTO)session.getAttribute("updatedCustomer");
        customer.setBookings(c.getBookings());
        customer.setSsn(c.getSsn());
        customerService.changeCustomer(customer);
        return "redirect:/customers";
    }

    @PostMapping("deleteCustomer")
//    @PreAuthorize("hasAuthority('Admin')")
    public String removeCustomer(@ModelAttribute("deleteCustomer") CustomerDetailedDTO customer) {
        customer.setBookings(new ArrayList<>());
        customerService.removeCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("UpdateCustomer")
    public String updateCustomer() {
        return "updateCustomerForm";
    }

    @GetMapping("customers/{id}")
    public String editCustomerById(@PathVariable Long id, RedirectAttributes rda, HttpSession session) {
        CustomerDetailedDTO customer = customerService.findCustomerById(id);

        session.setAttribute("updatedCustomer", customer);
        rda.addFlashAttribute("updatedCustomer", customer);
        return "redirect:/UpdateCustomer";
    }

    @GetMapping("newCurrentCustomer")
    public String newCurrentCustomer() {
        return "newCurrentCustomer";
    }
}
