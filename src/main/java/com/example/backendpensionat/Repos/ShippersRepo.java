package com.example.backendpensionat.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backendpensionat.Models.Shippers;


public interface ShippersRepo extends JpaRepository<Shippers,Long> {
}
