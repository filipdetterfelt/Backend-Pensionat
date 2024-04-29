package com.example.backendpensionat.Controllers;

import com.example.backendpensionat.DTO.BookingDTO;
import com.example.backendpensionat.DTO.CustomerDTO;
import com.example.backendpensionat.DTO.CustomerDetailedDTO;
import com.example.backendpensionat.DTO.RoomSearchDTO;
import com.example.backendpensionat.Models.Customer;
import com.example.backendpensionat.Services.CustomerService;
import com.example.backendpensionat.Services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("addCustomer")
    public String addCustomers(@ModelAttribute("newCustomers") CustomerDetailedDTO customer){
        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }

    @PostMapping("updateCustomer")
    public String updateCustomer(@ModelAttribute ("updateCustomers") CustomerDetailedDTO customer){
        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.changeCustomer(customer);
        return "redirect:/customers";
    }

    @PostMapping("deleteCustomer")
    public String removeCustomer(@ModelAttribute("deleteCustomer") CustomerDetailedDTO customer) {
        customerService.removeCustomer(customer);
        return "index";
    }

    @GetMapping("UpdateCustomer")
    public String updateCustomer() {
        return "updateCustomerForm";
    }
}
