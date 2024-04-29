package com.example.backendpensionat.Controllers;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("customers")
    public String customers(Model model) {
        model.addAttribute("customersList", customerService.listAllCustomers());
        return "customers";
    }



    @GetMapping("addNewCustomer")
    public String addNewCustomer() {
        return "addNewCustomerForm";
    }

    @PostMapping("addCustomer")
    public String addCustomers(@RequestBody CustomerDetailedDTO customer){
        customerService.addCustomer(customer);
        return "Customer has added";
    }


    @GetMapping("UpdateCustomer")
    public String updateCustomer() {
        return "updateCustomerForm";
    }
    @GetMapping("removeCustomer")
    public String removeCustomer() {
        return "removeCustomerField";
    }
}
