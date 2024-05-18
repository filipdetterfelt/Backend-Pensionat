package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerBySsn(String ssn);
}
