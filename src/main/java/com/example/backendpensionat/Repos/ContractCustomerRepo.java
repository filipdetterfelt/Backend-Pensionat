package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractCustomerRepo extends JpaRepository<ContractCustomer,Long>{
    Optional<ContractCustomer> findContractCustomerByExternalId(Long id);
}
