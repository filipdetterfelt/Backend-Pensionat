package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public class CustomerRepo extends JpaRepository<Customer, Long> {
}
