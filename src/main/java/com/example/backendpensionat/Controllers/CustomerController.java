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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("UpdateCustomers")
    public String updateCustomer(@ModelAttribute ("updateCustomers") CustomerDetailedDTO customer){
        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.changeCustomer(customer);
        return "redirect:/customers";
    }

    @PutMapping("changeCustomer")
    public List<CustomerDetailedDTO> changeCustomerById(@RequestBody CustomerDetailedDTO customer) {
        CustomerDetailedDTO updatedCustomer = customerService.changeCustomer(customer)
    }

    @PostMapping("deleteCustomer")
    public String removeCustomer(@ModelAttribute("deleteCustomer") CustomerDetailedDTO customer) {
        customer.setBookings(new ArrayList<BookingDTO>());
        customerService.removeCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("UpdateCustomer")
    public String updateCustomer() {
        return "updateCustomerForm";
    }
}
