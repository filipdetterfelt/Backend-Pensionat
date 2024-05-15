package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Shippers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippersRepo extends JpaRepository<Shippers, Long> {
    Optional<Shippers> findShippersByExternalId(Long id);
}