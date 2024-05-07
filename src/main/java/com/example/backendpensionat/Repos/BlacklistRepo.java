package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepo extends JpaRepository<Blacklist, Long> {
}
