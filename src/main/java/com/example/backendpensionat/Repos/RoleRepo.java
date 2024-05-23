package com.example.backendpensionat.Repos;

import com.example.backendpensionat.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
